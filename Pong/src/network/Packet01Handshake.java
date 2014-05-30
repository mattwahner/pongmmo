package network;

import java.io.PrintWriter;

public class Packet01Handshake extends Packet {

	private String test;
	private int test2;
	
	public Packet01Handshake(){
		test = "test";
		test2 = 123;
	}
	
	public Packet01Handshake(String test, int test2){
		this.test = test;
		this.test2 = test2;
	}
	
	public void writePacketData(PrintWriter pw) {
		String data = 1 + "~" + test + "~" + test2;
		pw.println(data);
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		test = dataSplit[1];
		test2 = Integer.parseInt(dataSplit[2]);
	}
	
	public String getTest(){
		return test;
	}
	
	public int getTest2(){
		return test2;
	}
	
	public String toString(){
		return "Packet01Handshake";
	}
	
}
