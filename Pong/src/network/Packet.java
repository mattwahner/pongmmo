package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {
	
	private static Map<Integer, Class<?>> packetIdToClassMap = new HashMap<Integer, Class<?>>();
	private static Map<Class<?>, Integer> packetClassToIdMap = new HashMap<Class<?>, Integer>();
	
	public static Packet getNewPacket(int id){
		try {
			return (Packet) packetIdToClassMap.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Packet readPacket(BufferedReader br){
		Packet packet = null;
		try {
			String data = br.readLine();
			packet = getNewPacket(Integer.parseInt(data.split("~")[0]));
			packet.readPacketData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	public static void writePacket(Packet packet, PrintWriter pw){
		packet.writePacketData(pw);
	}
	
	public int getPacketId(){
		return packetClassToIdMap.get(this.getClass());
	}
	
	public abstract void writePacketData(PrintWriter pw);
	public abstract void readPacketData(String s);
	
	private static void addClassIdMapping(int id, Class<?> cl){
		packetIdToClassMap.put(id, cl);
		packetClassToIdMap.put(cl, id);
	}
	
	static{
		addClassIdMapping(1, Packet01Handshake.class);
		addClassIdMapping(2, Packet02TestConnection.class);
		addClassIdMapping(3, Packet03Start.class);
		addClassIdMapping(4, Packet04PlayerMove.class);
		addClassIdMapping(5, Packet05PlayerPos.class);
	}
	
}
