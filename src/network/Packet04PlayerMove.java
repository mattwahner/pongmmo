package network;

import java.io.PrintWriter;

public class Packet04PlayerMove extends Packet {

	private String team;
	private int dir;
	
	public Packet04PlayerMove(){
		team = "None";
		dir = 0;
	}
	
	public Packet04PlayerMove(String team, int dir){
		this.team = team;
		this.dir = dir;
	}
	
	public void writePacketData(PrintWriter pw) {
		String data = this.getPacketId() + "~" + team + "~" + dir;
		pw.println(data);
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		team = dataSplit[1];
		dir = Integer.parseInt(dataSplit[2]);
	}
	
	public String getTeam(){
		return team;
	}
	
	public int getDir(){
		return dir;
	}
	
	public String toString(){
		return "Packet04Move";
	}

}
