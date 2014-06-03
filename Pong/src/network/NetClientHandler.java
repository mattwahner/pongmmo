package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class NetClientHandler extends NetHandler {

	private TcpConnection tc;
	private NetClientHandlerThread ncht;
	
	private static int numNCHs = 0;
	
	public NetClientHandler(String inetAddress, int port){
		try {
			tc = new TcpConnection(new Socket(inetAddress, port), inetAddress + " netClientHandler");
		} catch (IOException e) {
			e.printStackTrace();
			tc = null;
		}
		ncht = new NetClientHandlerThread(this, numNCHs + " NetClientHandler");
		numNCHs++;
		ncht.start();
	}
	
	public void shutdownConnection(){
		tc.shutdownConnection();
	}
	
	//TODO: Get rid of this later
	public TcpConnection getConnection(){
		return tc;
	}
	
	public ArrayList<Packet> getRecvListPackets(){
		return tc.getOutstandingPackets();
	}
	
	public void handleTest(String test, int i) {
		tc.addToSendQue(new Packet01Handshake(test, i));
	}
	
	public void checkConnection(){
		ArrayList<Packet> checkPackets = tc.getOutstandingPackets(2);
		for(int i = 0; i < checkPackets.size(); i++){
			tc.addToSendQue(new Packet02TestConnection());
		}
	}
	
	static void checkNetworkConnection(NetClientHandler nch){
		nch.checkConnection();
	}
	
}
