package network;

import java.util.ArrayList;

public class NetServerHandler extends NetHandler {
	
	private TcpConnection tc;
	
	public NetServerHandler(TcpConnection tc){
		this.tc = tc;
	}
	
	public ArrayList<Packet> getRecvListPackets(int id){
		return tc.getOutstandingPackets(id);
	}
	
	public void handleLogin(String username) {
		tc.addToSendQue(new Packet01Handshake(username));
	}
	
}
