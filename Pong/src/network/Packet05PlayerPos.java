package network;

import java.io.PrintWriter;

import main.Player;
import main.Pong;

public class Packet05PlayerPos extends Packet {

	private String team;
	private int pos;
	
	public Packet05PlayerPos(){
		team = "None";
		pos = (Pong.HEIGHT / 2) - (Player.HEIGHT / 2);
	}
	
	public Packet05PlayerPos(String team, int pos){
		this.team = team;
		this.pos = pos;
	}
	
	public void writePacketData(PrintWriter pw) {
		String data = this.getPacketId() + "~" + team + "~" + pos;
		pw.println(data);
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		team = dataSplit[1];
		pos = Integer.parseInt(dataSplit[2]);
	}
	
	public String getTeam(){
		return team;
	}
	
	public int getPos(){
		return pos;
	}
	
	public String toString(){
		return "Packet05PlayerPos";
	}

}
