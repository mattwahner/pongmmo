package network;

import java.util.ArrayList;

public abstract class NetHandler {
	
	public abstract ArrayList<Packet> getRecvListPackets();
	public abstract void handleTest(Packet01Handshake packet);
	
}
