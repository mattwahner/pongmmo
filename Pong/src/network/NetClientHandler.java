package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class NetClientHandler extends NetHandler {

	TcpConnection tc;
	
	public NetClientHandler(String inetAddress, int port){
		try {
			tc = new TcpConnection(new Socket(inetAddress, port), inetAddress + "netClientHandler");
		} catch (IOException e) {
			e.printStackTrace();
			tc = null;
		}
	}
	
	public void shutdownConnection(){
		tc.shutdownConnection();
		tc = null;
	}
	
	//TODO: Get rid of this later
	public TcpConnection getConnection(){
		return tc;
	}
	
	public ArrayList<Packet> getRecvListPackets(){
		return tc.processPackets();
	}
	
	public void handleTest(String test, int i) {
		tc.addToSendQue(new Packet01Handshake(test, i));
	}
	
}
