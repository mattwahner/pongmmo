package network;

import java.util.ArrayList;

public class NetServerHandler extends NetHandler {
	
	private TcpConnection tc;
	
	public NetServerHandler(TcpConnection tc){
		this.tc = tc;
	}
	
	public ArrayList<Packet> getRecvListPackets(){
		return tc.getRecvList(true);
	}
	
	public void handleTest(String test, int i) {
		tc.addToSendQue(new Packet01Handshake(test, i));
	}
	
}
