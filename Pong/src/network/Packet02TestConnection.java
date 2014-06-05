package network;

import java.io.PrintWriter;

public class Packet02TestConnection extends Packet {
	
	private String test;
	
	public void writePacketData(){}
	
	public void writePacketData(PrintWriter pw) {
		pw.println(this.getPacketId() + "~test");
	}

	public void readPacketData(String s) {
		String[] dataSplit = s.split("~");
		test = dataSplit[1];
	}
	
	public boolean correct(){
		return test.equals("test");
	}

}
