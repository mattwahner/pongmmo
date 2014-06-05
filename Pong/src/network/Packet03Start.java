package network;

import java.io.PrintWriter;

public class Packet03Start extends Packet {
	
	public void writePacketData(PrintWriter pw) {
		pw.println(this.getPacketId());
	}

	public void readPacketData(String s) {}

}
