package network;

import java.util.ArrayList;

public abstract class NetHandler {
	
	public abstract ArrayList<Packet> getRecvListPackets(int id);
	public abstract void handleLogin(String username);
	
}
