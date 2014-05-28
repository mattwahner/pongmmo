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
	
	public void handleTest(Packet01Handshake packet) {
		System.out.println("Prepping packets");
		for(TcpConnection c : connections){
			System.out.println("Sending packet");
			c.addToSendQue(packet);
		}
	}
	
}
