package network;

import java.io.PrintWriter;

public class Packet01Handshake extends Packet {

	private String username;
	private String team;
	
	public Packet01Handshake(){
		username = "Anonymous";
		team = "None";
	}
	
	public Packet01Handshake(String username, String team){
		this.username = username;
		this.team = team;
	}
	
	public void writePacketData(PrintWriter pw) {
		String data = this.getPacketId() + "~" + username + "~" + team;
		pw.println(data);
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		username = dataSplit[1];
		team = dataSplit[2];
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getTeam(){
		return team;
	}
	
	public String toString(){
		return "Packet01Handshake";
	}
	
}
