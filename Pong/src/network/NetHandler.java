package network;

import java.util.ArrayList;

public abstract class NetHandler {
	
	public abstract ArrayList<Packet> getRecvListPackets(int id);
	public abstract void handleLogin(String username, String team);
	public abstract void handleStart();
	public abstract void handlePlayerMove(String team, int dir);
	public abstract void handlePlayerPos(String team, int pos);
	
}
