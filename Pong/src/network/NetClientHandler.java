package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import main.Player;
import main.Pong;

public class NetClientHandler extends NetHandler {

	private TcpConnection tc;
	private ClientHandlerListenThread chlt;
	private ClientHandlerPacketThread chpt;
	
	private static int numNCHs = 0;
	
	private String team = "None";
	private int playerLeftPos = (Pong.HEIGHT / 2) - (Player.HEIGHT / 2);
	private int playerRightPos = (Pong.HEIGHT / 2) - (Player.HEIGHT / 2);
	private boolean isStarted = false;
	
	public NetClientHandler(String inetAddress, int port){
		try {
			tc = new TcpConnection(new Socket(inetAddress, port), inetAddress + " NetClientHandler");
		} catch (IOException e) {
			e.printStackTrace();
			tc = null;
		}
		chlt = new ClientHandlerListenThread(this, numNCHs + " NetClientHandler");
		chpt = new ClientHandlerPacketThread(this, numNCHs + " Packet Handler");
		numNCHs++;
		chlt.start();
		chpt.start();
	}
	
	public void shutdownConnection(){
		tc.shutdownConnection();
		chlt.terminate();
		chpt.terminate();
	}
	
	public ArrayList<Packet> getRecvListPackets(int id){
		return tc.getOutstandingPackets(id);
	}
	
	public void handleLogin(String username, String team) {
		tc.addToSendQue(new Packet01Handshake(username, team));
	}
	
	public void handleStart(){
		tc.addToSendQue(new Packet03Start());
	}
	
	public void handlePlayerMove(String team, int dir){
		tc.addToSendQue(new Packet04PlayerMove(team, dir));
	}
	
	public void handlePlayerPos(String team, int pos){
		tc.addToSendQue(new Packet05PlayerPos(team, pos));
	}
	
	public boolean isConnected(){
		return tc.isConnected();
	}
	
	public String getTeam(){
		return team;
	}
	
	public boolean isStarted(){
		return isStarted;
	}
	
	public int getPlayerLeftPos(){
		return playerLeftPos;
	}
	
	public int getPlayerRightPos(){
		return playerRightPos;
	}
	
	public void checkConnection(){
		ArrayList<Packet> checkPackets = tc.getOutstandingPackets(2);
		for(int i = 0; i < checkPackets.size(); i++){
			tc.addToSendQue(new Packet02TestConnection());
		}
	}
	
	public void processPackets(){
		ArrayList<Packet> teamPackets = getRecvListPackets(1);
		if(teamPackets.size() > 0) team = ((Packet01Handshake) teamPackets.get(0)).getTeam();
		ArrayList<Packet> startPackets = getRecvListPackets(3);
		if(startPackets.size() > 0) isStarted = true;
		ArrayList<Packet> posPackets = getRecvListPackets(5);
		for(Packet p : posPackets){
			if(((Packet05PlayerPos) p).getTeam().equals("Left")) playerLeftPos = ((Packet05PlayerPos) p).getPos();
			if(((Packet05PlayerPos) p).getTeam().equals("Right")) playerRightPos = ((Packet05PlayerPos) p).getPos();
		}
	}
	
	static void checkNetworkConnection(NetClientHandler nch){
		nch.checkConnection();
	}
	
	static void processNetPackets(NetClientHandler nch){
		nch.processPackets();
	}
	
}
