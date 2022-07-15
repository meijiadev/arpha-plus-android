package com.shangyun.p2ptester.Model;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.shangyun.p2ptester.Util.LogUtil;
import com.shangyun.p2ptester.Util.P2PUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WakeupQuery {

    public static final int ERROR_Wakeup_NoLogin            = -1;
    public static final int ERROR_Wakeup_InvalidParameter   = -2;
    public static final int ERROR_Wakeup_SocketCreateFailed = -3;
    public static final int ERROR_Wakeup_SendToFailed       = -4;
    public static final int ERROR_Wakeup_StringEncFailed    = -5;
    public static final int ERROR_Wakeup_UnKnown            = -99;

    public HashMap<String, Integer> lastLoginMap;
    public boolean stopQuery;
    public int repeatNumber;
    public int socketTimeout;

    public WakeupQuery() {
        this.repeatNumber = 5;
        this.socketTimeout = 3000;
        lastLoginMap = new HashMap<>();
    }

    @SuppressLint("DefaultLocale")
    public int query(String did, String wakeupKey, List<String> ips) {

        if (TextUtils.isEmpty(did)) { return ERROR_Wakeup_InvalidParameter; }
        if (wakeupKey == null || wakeupKey.length() != 16) { return ERROR_Wakeup_InvalidParameter; }
        if (ips == null || ips.size() == 0) { return ERROR_Wakeup_InvalidParameter; }

        HashMap<String, Integer> timeCount = new HashMap<>();
        LogUtil.d(String.format("WakeUp_Query(%s, %s, %s) start...", did, wakeupKey, ips));
        for (String ip : ips) {
            if (ip != null && !"".equals(ip)) {
                lastLoginMap.put(ip, ERROR_Wakeup_UnKnown);
                timeCount.put(ip, 0);
            }
        }
        if (lastLoginMap.size() == 0) return ERROR_Wakeup_InvalidParameter;

        LogUtil.d("map: " + lastLoginMap);

        byte[] cmd_enc = new byte[64];
        Arrays.fill(cmd_enc, (byte)0);
        String cmd = "DID=" + did + "&";
        if (P2PUtil.iPN_StringEnc(wakeupKey.getBytes(), cmd.getBytes(), cmd_enc, cmd_enc.length) < 0) {
            LogUtil.d("iPN_StringEnc fail, wakeup query exit...");
            return ERROR_Wakeup_StringEncFailed;
        }

        byte[] receiveBuff = new byte[256];
        byte[] message = new byte[128];
        int port = 12305;
        int time_out_ms = socketTimeout;

        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(time_out_ms);
        } catch (SocketException e) {
            LogUtil.e("SocketException: " + e.getMessage());
            return ERROR_Wakeup_SocketCreateFailed;
        }

        int queryTime = 0;
        int readDataCount;
        stopQuery = false;
        while (!stopQuery && queryTime++ < repeatNumber) {

            readDataCount = 0;
            for (String ip : lastLoginMap.keySet()) {

                Integer integer = lastLoginMap.get(ip);
                if (integer != null && (integer == ERROR_Wakeup_UnKnown || integer == ERROR_Wakeup_SendToFailed)) {

                    Integer qt = timeCount.get(ip);
                    Long nt = System.currentTimeMillis() / 1000;
                    Integer t2 = nt.intValue();
                    if (qt == null || nt - qt > 1) {
                        timeCount.put(ip, t2);
                        InetAddress address;
                        try {
                            address = InetAddress.getByName(ip);
                        } catch (UnknownHostException e) {
                            LogUtil.e(String.format("%d-IP(%s) UnknownHostException: %s", queryTime, ip, e.getMessage()));
                            continue;
                        }
                        LogUtil.d(String.format("%d-IP(%s) get InetAddress: %s", queryTime, ip, address.getHostAddress()));

                        DatagramPacket packet = new DatagramPacket(cmd_enc, cmd_enc.length, address, port);
                        try {
                            socket.send(packet);
                            LogUtil.d(String.format("%d-IP(%s) send packet.", queryTime, ip));
                        } catch (IOException e) {
                            LogUtil.d(String.format("%d-IP(%s) send packet IOException: %s", queryTime, ip, e.getMessage()));
                            lastLoginMap.put(ip, ERROR_Wakeup_SendToFailed);
                        }
                    } else {
                        LogUtil.d(ip + ": now_time=" + nt + ", query_time=" + qt);
                    }
                } else readDataCount++;
            }
            if (readDataCount == lastLoginMap.size()) break;

            Arrays.fill(receiveBuff, (byte)0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuff, receiveBuff.length);

            try {
                socket.receive(receivePacket);
            } catch (IOException e) {
                LogUtil.e("receive IOException: " + e.getMessage());
                continue;
            }

            Arrays.fill(message, (byte)0);
            if (P2PUtil.iPN_StringDnc(wakeupKey.getBytes(), receiveBuff, message, message.length) < 0) {
                LogUtil.d( " - iPN_StringDnc failed!");
                continue;
            }

            String MSG = new String(message, 0, P2PUtil.getValidLength(message));
            LogUtil.d( queryTime + "-receive msg: " + MSG);
            int lastLogin;
            for (String s : MSG.split("&")) {
                if (s.contains("LastLogin")) {
                    lastLogin = Integer.parseInt(s.split("=")[1]);
                    LogUtil.d(String.format("%d-IP(%s) lastSleepLogin: %d", queryTime, receivePacket.getAddress().getHostAddress(), lastLogin));
                    for (String ip : ips) {
                        if (receivePacket.getAddress().getHostAddress().equalsIgnoreCase(ip)) {
                            lastLoginMap.put(ip, lastLogin);
                        }
                    }
                }
            }
        }

        int lastSleepLogin = getMiniSleepLoginTime();

        LogUtil.d("WakeUp_Query() done, lastSleepLogin=" + lastSleepLogin);

        return lastSleepLogin;
    }

    private int getMiniSleepLoginTime() {

        if (lastLoginMap.size() == 0) return ERROR_Wakeup_UnKnown;

        LogUtil.d("map: " + lastLoginMap);

        int mini = -99;
        for (Integer value : lastLoginMap.values()) {
            if (value < 0) {
                if (mini < value) mini = value;
            } else if (mini < 0 || mini > value) {
                mini = value;
            }
        }

        return mini;
    }
}
