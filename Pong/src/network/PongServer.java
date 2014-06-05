package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Player;
import main.Pong;

public class PongServer {

	private static int clientCount = 0;
	
	private ServerSocket server;
	private ServerListenThread serverListenThread;
	private ServerPacketThread serverPacketThread;
	
	private int leftTeamCount = 0;
	private int rightTeamCount = 0;
	private int playerLeftY = (Pong.HEIGHT / 2) - (Player.HEIGHT / 2);
	private int playerRightY = (Pong.HEIGHT / 2) - (Player.HEIGHT / 2);
	
	private List<NetworkPlayer> players = Collections.synchronizedList(new ArrayList<NetworkPlayer>());
	
	public PongServer(int port){
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverListenThread = new ServerListenThread(this, port + " listen thread");
		serverListenThread.start();
		serverPacketThread = new ServerPacketThread(this, port + " packet thread");
		serverPacketThread.start();
	}
	
	public void startGame(){
		synchronized (players){
			for(NetworkPlayer p : players){
				p.getConnection().addToSendQue(new Packet03Start());
			}
		}
	}
	
	public List<NetworkPlayer> getPlayers(){
		return players;
	}
		
	public void acceptConnection(){
		synchronized (players){
			try {
				Socket clientSocket = server.accept();
				players.add(new NetworkPlayer(new TcpConnection(clientSocket, String.valueOf(clientCount))));
				clientCount++;
			} catch (IOException e){
				;
			}
		}
	}
	
	public void checkConnections(){
		synchronized (players){
			ArrayList<NetworkPlayer> tempList = new ArrayList<NetworkPlayer>(players);
			for(NetworkPlayer p : tempList){
				if(!p.getIsConnected()){
					if(p.getTeam().equals("Left")) leftTeamCount--;
					else if(p.getTeam().equals("Right")) rightTeamCount--;
					p.shutdown();
					players.remove(p);
				}
			}
		}
	}
	
	public void processPackets(){
		synchronized (players){
			for(NetworkPlayer p : players){
				ArrayList<Packet> handshakes = p.getConnection().getOutstandingPackets(1);
				if(handshakes.size() > 0){
					p.setUsername(((Packet01Handshake) handshakes.get(0)).getUsername());
					if(leftTeamCount <= rightTeamCount){
						p.setTeam("Left");
						p.getNetServerHandler().handleLogin("Anonymous", "Left");
						leftTeamCount++;
					}
					else{
						p.setTeam("Right");
						p.getNetServerHandler().handleLogin("Anonymous", "Right");
						rightTeamCount++;
					}
				}
			}
			double speedRatio = Player.SPEED / (players.size() / 2.0);
			ArrayList<Packet04PlayerMove> moves = new ArrayList<Packet04PlayerMove>();
			for(NetworkPlayer p : players){
				for(Packet packet : p.getConnection().getOutstandingPackets(4)){
					moves.add((Packet04PlayerMove) packet);
				}
			}
			ArrayList<Packet04PlayerMove> leftMoves = new ArrayList<Packet04PlayerMove>();
			ArrayList<Packet04PlayerMove> rightMoves = new ArrayList<Packet04PlayerMove>();
			for(Packet04PlayerMove packet : moves){
				if(packet.getTeam().equals("Left")) leftMoves.add(packet);
				if(packet.getTeam().equals("Right")) rightMoves.add(packet);
			}
			int sumLeftY = 0;
			int sumRightY = 0;
			for(Packet04PlayerMove packet : leftMoves) sumLeftY += packet.getDir();
			for(Packet04PlayerMove packet : rightMoves) sumRightY += packet.getDir();
			playerLeftY += (sumLeftY * speedRatio);
			playerRightY += (sumRightY * speedRatio);
			for(NetworkPlayer p : players){
				p.getNetServerHandler().handlePlayerPos("Left", playerLeftY);
				p.getNetServerHandler().handlePlayerPos("Right", playerRightY);
			}
		}
	}
	
	static void acceptNetworkConnection(PongServer server){
		server.acceptConnection();
	}
	
	static void checkNetworkConnections(PongServer server){
		server.checkConnections();
	}
	
	static void processNetworkPackets(PongServer server){
		server.processPackets();
	}
	
}
