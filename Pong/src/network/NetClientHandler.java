package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class NetClientHandler extends NetHandler {

	TcpConnection connection;
	
	public NetClientHandler(String inetAddress, int port){
		try {
			connection = new TcpConnection(new Socket(inetAddress, port), inetAddress);
		} catch (IOException e) {
			e.printStackTrace();
			connection = null;
		}
	}
	
	public ArrayList<Packet> getRecvListPackets(){
		return connection.getRecvList();
	}
	
	public void handleTest(String test, int i) {
		connection.addToSendQue(new Packet01Handshake(test, i));
	}
	
}
