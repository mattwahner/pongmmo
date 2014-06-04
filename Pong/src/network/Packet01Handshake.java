package network;

import java.io.PrintWriter;

public class Packet01Handshake extends Packet {

	private String username;
	
	public Packet01Handshake(){
		username = "Anonymous";
	}
	
	public Packet01Handshake(String test){
		this.username = test;
	}
	
	public void writePacketData(PrintWriter pw) {
		String data = 1 + "~" + username;
		pw.println(data);
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		username = dataSplit[1];
	}
	
	public String getUsername(){
		return username;
	}
	
	public String toString(){
		return "Packet01Handshake";
	}
	
}
