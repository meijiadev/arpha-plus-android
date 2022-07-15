package com.p2p.pppp_api;

public class st_PPCS_NetInfo {
	byte bFlagInternet		=0;
	byte bFlagHostResolved	=0;
	byte bFlagServerHello	=0;
	byte NAT_Type			=0;
	byte[] MyLanIP=new byte[16];
	byte[] MyWanIP=new byte[16];
	public int LanPort;
    public int WanPort;
    
	public int getbFlagInternet() { return bFlagInternet&0xFF;		}
	public int getbFlagHostResolved() { return bFlagHostResolved&0xFF;		}
	public int getbFlagServerHello() { return bFlagServerHello&0xFF;		}
	public int getNAT_Type() { return NAT_Type&0xFF;		}
	public String getMyLanIP() { return st_PPCS_Session.bytes2Str(MyLanIP); }
	public String getMyWanIP() { return st_PPCS_Session.bytes2Str(MyWanIP); }	
	
	public int	  getLanPort()	{ return LanPort;			}
	public int	  getWanPort()	{ return WanPort;				}
}
