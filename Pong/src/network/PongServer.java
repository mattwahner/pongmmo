package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PongServer {

	private static int clientCount = 0;
	
	private ServerSocket server;
	private ServerListenThread pongServerThread;
	private ServerPacketThread serverPacketThread;
	
	private ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
	
	public PongServer(int port){
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pongServerThread = new ServerListenThread(this, port + " listen thread");
		pongServerThread.start();
		serverPacketThread = new ServerPacketThread(this, port + " packet thread");
		serverPacketThread.start();
	}
	
	public ArrayList<NetworkPlayer> getPlayers(){
		return players;
	}
		
	public void acceptConnection(){
		try {
			Socket clientSocket = server.accept();
			players.add(new NetworkPlayer(new TcpConnection(clientSocket, String.valueOf(clientCount))));
			clientCount++;
		} catch (IOException e){
			;
		}
	}
	
	public void checkConnections(){
		ArrayList<NetworkPlayer> tempList = new ArrayList<NetworkPlayer>(players);
		for(NetworkPlayer p : tempList){
			if(p.getConnection().getIsClosed()){
				p.getConnection().shutdownConnection();
				players.remove(p);
			}
		}
	}
	
	public void redirectPackets(){
		ArrayList<Packet> outstandingPackets = new ArrayList<Packet>();
		for(NetworkPlayer p : players){
			outstandingPackets.addAll(p.getConnection().processPackets());
		}
		for(NetworkPlayer player : players){
			TcpConnection tc = player.getConnection();
			for(Packet p : outstandingPackets) tc.addToSendQue(p);
		}
	}
	
	static void acceptNetworkConnection(PongServer server){
		server.acceptConnection();
	}
	
	static void checkNetworkConnections(PongServer server){
		server.acceptConnection();
	}
	
	static void redirectNetworkPackets(PongServer server){
		server.redirectPackets();
	}
	
}
