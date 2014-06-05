package network;

import java.util.ArrayList;

public class NetServerHandler extends NetHandler {
	
	private TcpConnection tc;
	
	public NetServerHandler(TcpConnection tc){
		this.tc = tc;
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
	
}
