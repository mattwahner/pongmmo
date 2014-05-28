package network;

import java.util.ArrayList;

public abstract class NetHandler {
	
	public abstract ArrayList<Packet> getRecvListPackets();
	public abstract void handleTest(String test, int i);
	
}
