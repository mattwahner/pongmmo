package network;

import java.util.ArrayList;

public class NetServerHandler extends NetHandler {

	private PongServer pongServer;
	
	private ArrayList<TcpConnection> connections = new ArrayList<TcpConnection>();
	
	public NetServerHandler(int port){
		pongServer = new PongServer(port);
	}
	
	public void updateConnections(){
		connections = pongServer.getConnections();
	}
	
	public ArrayList<Packet> getRecvListPackets(){
		ArrayList<Packet> allPackets = new ArrayList<Packet>();
		for(TcpConnection c : connections){
			allPackets.addAll(c.getRecvList());
		}
		for(Packet p : allPackets){
			for(TcpConnection c : connections){
				c.addToSendQue(p);
			}
		}
		return allPackets;
	}
	
	public void handleTest(String test, int i) {
		for(TcpConnection c : connections){
			c.addToSendQue(new Packet01Handshake(test, i));
		}
	}
	
}
