package packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PacketHandler implements Runnable {

	private Packet packet;
	
	private boolean running = false;
	private boolean isServer;
	private ServerSocket serverSocket;
	private ArrayList<Socket> clientSocket = new ArrayList<Socket>();
	private boolean connectionMade = false;
	private ArrayList<PrintWriter> out = new ArrayList<PrintWriter>();
	private ArrayList<BufferedReader> in = new ArrayList<BufferedReader>();
	private ArrayList<Packet> packetQue = new ArrayList<Packet>();
	private ArrayList<Packet> outStandingPackets = new ArrayList<Packet>();
	
	public PacketHandler(int port){
		isServer = true;
		try {
			serverSocket = new ServerSocket(port);
			connectionMade = true;
		} catch (IOException e) {
			System.err.println("ERROR: Connection was not made! Server not created correctly!");
			e.printStackTrace();
		}
	}
	
	public PacketHandler(String inetAddress, int port){
		isServer = false;
		packet = new Packet();
		try {
			clientSocket.add(new Socket(inetAddress, port));
			out.add(new PrintWriter(clientSocket.get(0).getOutputStream(), true));
			in.add(new BufferedReader(new InputStreamReader(clientSocket.get(0).getInputStream())));
			connectionMade = true;
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
			for(Socket s : clientSocket) s.close();
			clientSocket.clear();
			for(PrintWriter p : out) p.close();
			out.clear();
			for(BufferedReader b : in) b.close();
			in.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		if(isServer) new SocketAccept(this, serverSocket).start();
		System.out.println("Past adding connetions");
		while(running){
			if(packetQue.size() > 0){
				if(isServer){
					for(PrintWriter pw : out){
						for(Packet p : packetQue) pw.println(p.getPacketData());
					}
				}else{
					for(Packet p : packetQue) out.get(0).println(p.getPacketData());
					packetQue.clear();
				}
			}
			try {
				if(isServer){
					for(BufferedReader b : in){
						while(b.ready()){
							System.out.println("Server recieved packet!");
							String[] inputSlice = b.readLine().split("|");
							int id = Integer.parseInt(inputSlice[0]);
							try {
								Packet newPacket = packet.getPacketList().get(id).getClass().newInstance();
								System.out.println(newPacket.getClass());
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
							
						}
					}
				}else{
					while(in.get(0).ready()){
						String[] inputSlice = in.get(0).readLine().split("|");
						int id = Integer.parseInt(inputSlice[0]);
						try {
							Packet newPacket = packet.getPacketList().get(id).getClass().newInstance();
							System.out.println(newPacket.getClass());
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}

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
	
	public void addSocket(Socket s){
		clientSocket.add(s);
	}
	
	public void addOut(PrintWriter p){
		out.add(p);
	}
	
	public void addIn(BufferedReader b){
		in.add(b);
	}
	
	public boolean getConnectionMade(){
		return connectionMade;
	}
	
}
