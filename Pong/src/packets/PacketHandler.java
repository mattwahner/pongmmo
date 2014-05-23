package packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class PacketHandler implements Runnable {

	private Packet packet;
	
	private boolean running = false;
	private Socket clientSocket;
	private boolean connectionMade = false;
	private PrintWriter out;
	private BufferedReader in;
	private ArrayList<Packet> packetQue = new ArrayList<Packet>();
	private ArrayList<Packet> outStandingPackets = new ArrayList<Packet>();
	
	public PacketHandler(String inetAddress, int port){
		packet = new Packet();
		try {
			clientSocket = new Socket(inetAddress, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			connectionMade = true;
			start();
		} catch (IOException e) {
			System.err.println("ERROR: Connection was not made!");
			e.printStackTrace();
		}
	}
	
	public void start(){
		running = true;
		new Thread(this).run();
	}
	
	public void stop(){
		running = false;
		try {
			clientSocket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(running){
			if(packetQue.size() > 0){
				for(Packet p : packetQue) out.println(p.getPacketData());
				packetQue.clear();
			}
			try {
				while(in.ready()){
					String[] inputSlice = in.readLine().split("|");
					int id = Integer.parseInt(inputSlice[0]);
					try {
						Packet newPacket = packet.getPacketList().get(id++).getClass().newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addPacketToQue(Packet packet){
		packetQue.add(packet);
	}
	
	public ArrayList<Packet> getOutstandingPackets(){
		ArrayList<Packet> tempPackets = new ArrayList<Packet>();
		tempPackets.addAll(outStandingPackets);
		if(tempPackets.size() > 0){
			outStandingPackets.clear();
			return tempPackets;
		}
		else return null;
	}
	
	public boolean getConnectionMade(){
		return connectionMade;
	}
	
}
