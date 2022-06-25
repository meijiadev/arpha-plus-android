package com.cs2.sps;
import android.util.Log;

@SuppressWarnings("JniMissingFunction")
public class SPS_API {
// Error Code
    public static final int ERROR_SPS_NoError = 0;              //// API is successfully executed
    public static final int ERROR_SPS_InvalidParameter = -1;    //// Invalid Parameter
    public static final int ERROR_SPS_InvalidFunCode = -2;      //// Invalid FunCode
    public static final int ERROR_SPS_TimeOut = -3;             //// TimeOut (over 5 sec) waiting for Server's response
    public static final int ERROR_SPS_AuthWordError = -4;       //// AuthWord mismatch
    public static final int ERROR_SPS_FileNotExist = -5;        //// FileName doesn't exist
    public static final int ERROR_SPS_BufferNotEnough = -6;     //// Snapshot buffer size is too samll
    public static final int ERROR_SPS_UserBreak = -7;           //// User break, ie CS2_SPS_Break() is called
    public static final int ERROR_SPS_NotEnoughMemory = -8;     //// The system memory is not enough for malloc
    public static final int ERROR_SPS_InvalidDID = -9;          //// Something wrong with 'DID'
    public static final int ERROR_SPS_ServerCloseSession = -10; //// Server has closed tcp session, please look up Server Log for what happened.
    public static final int ERROR_SPS_LocalSocketTimeout = -11; //// Local socket read timeout (5 sec)
    public static final int ERROR_SPS_UploadIsNotRunning = -12; //// SPS_Upload (with Parameter SnapshotBuf = NULL) is not calling
    public static final int ERROR_SPS_ExceedMaxPINFOSize = -13;         //// The PINFO must be less than 4095 Bytes
    public static final int ERROR_SPS_ExceedMaxAINFOSize = -14;         //// The AINFO must be less than 4095 Bytes
    public static final int ERROR_SPS_ExceedMaxSnapshotSize = -15;      //// The Snapshot must be less than 32 MBytes
    public static final int ERRPR_SPS_UploadTemporaryDisabled = -16;    //// The SPS_Upload function is temporarily disabled, due to too many Upload in last 10 minutes
    public static final int ERROR_SPS_ServerError = -98;        //// Server Error, Please check log of all CS2 SPS Servers, or contact CS2 FAE.
    public static final int ERROR_SPS_UnknownError = -99;       //// Unknown Error, please contact CS2 FAE.

// API
    public static native void CS2_SPS_Initialize();
    public static native void CS2_SPS_DeInitialize();
    public static native String CS2_SPS_GetAPIVersion(int[] Version);
    public static native int CS2_SPS_Upload(String SPSInfo, String AuthWord, String DID, String LNS, int CH, String PINFO, String AINFO, String SnapshotBuf, int SSBufSize, byte[] PostResultBuf, int PRBufSize);
// SPSInfo: The Server information of SPS. This information is provided by QS of WiPN.
// AuthWord: The authentication word. This must match SPS' setting otherwise SPS Server will not accept connection. AuthWord can be at most 16 characters. If exceeds 16 characters, only the fist 16 characters are compared.
// DID, LNS, CH, ,PINFO, AINFO: Information needed for Post action.
// SnapshotBuf: The buffer of snapshot picture.
// SSBufSize: size of SnapshotBuf
// PostResultBuf: If PostResultBuf != NULL && PRBufSize != 0,  CS2s3_Upload() will wait till post action finished and post result is stored in PostResultBuf. If PostResultBuf == NULL, CS2s3_Upload() will not waiting for post result.
// PRBufSize: size of PostResultBuf. If PRBufSize is not big enough, the post result will be truncated.

    public static native int CS2_SPS_Download(String DLInfo, String AuthWord, String FileName, byte[] SnapshotBuf, int[] SBufSize, char FunCode);
// DLInfo The IP:Port information to download snapshot. DLInfo is inside the payload section of push notify content.
// AuthWord: The authentication word. This must match SPS' setting otherwise SPS Server will not accept connection. AuthWord can be at most 16 characters. If exceeds 16 characters, only the fist 16 characters are compared.
// FileName: the FileName of snapshot. DLInfo and FileName is provided in payload section of Push notify content.
// SnapshotBuf: The buffer for download snapshot file. The actual file size of Snapshot is provided in payload of Push notify content. So, you must declare SnapshotBuf with size > file size.
// BufSize: When calling, it specify the sizeof SnapshotBuf. When returned, it is the actual size of SnapshotBuf downloaded.
// FunCode: 0: Download only, 1: Download and Delete file, 2: Delete file only (don・t download)

    public static native void CS2_SPS_Break();
    //to break executing of CS2_SPS_Upload and CS2_SPS_Download.

    static {
        try {
            System.loadLibrary("SPS_API");
            Log.v("", "SPS_API loaded!");

        } catch (UnsatisfiedLinkError ule) {
            System.out.println("!!!!!!!!!  loadLibrary(SPS_API), Error:" + ule.getMessage());
        }
    }

}