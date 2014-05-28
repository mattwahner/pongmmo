package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PongServer {

	private int clientCount = 0;
	
	private ServerSocket server;
	private PongServerThread pongServerThread;
	
	private ArrayList<TcpConnection> connections = new ArrayList<TcpConnection>();
	
	public PongServer(int port){
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pongServerThread = new PongServerThread(this, port + " thread");
		pongServerThread.start();
	}
	
	public ArrayList<TcpConnection> getConnections(){
		return connections;
	}
	
	public void acceptConnection(){
		try {
			Socket clientSocket = server.accept();
			connections.add(new TcpConnection(clientSocket, String.valueOf(clientCount)));
			clientCount++;
		} catch (IOException e){
			;
		}
	}
	
	public void checkConnections(){
		ArrayList<TcpConnection> tempList = new ArrayList<TcpConnection>(connections);
		for(TcpConnection tc : tempList){
			if(tc.getIsClosed()) connections.remove(tc);
		}
	}
	
	static void acceptNetworkConnection(PongServer server){
		server.acceptConnection();
	}
	
	static void checkNetworkConnections(PongServer server){
		server.checkConnections();
	}
	
}
