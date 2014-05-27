package packets;

public class Packet01Handshake extends Packet {

	public int getPacketID(){
		return 0;
	}
	
	public String getPacketData(){
		return "01|test";
	}
	
}
