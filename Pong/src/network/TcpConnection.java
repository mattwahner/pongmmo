package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TcpConnection {
	
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;
	
	private ArrayList<Packet> sendQue = new ArrayList<Packet>();
	private ArrayList<Packet> recvQue = new ArrayList<Packet>();
	private ArrayList<Packet> outstandingPackets = new ArrayList<Packet>();
	private TcpReadThread readThread;
	private TcpWriteThread writeThread;
	
	public TcpConnection(Socket socket, String threadString){
		try {
			this.socket = socket;
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		readThread = new TcpReadThread(this, threadString + " read thread");
		writeThread = new TcpWriteThread(this, threadString + " write thread");
		readThread.start();
		writeThread.start();
	}
	
	public void addToSendQue(Packet p){
		sendQue.add(p);
	}
	
	public ArrayList<Packet> processPackets(){
		outstandingPackets.addAll(recvQue);
		ArrayList<Packet> tempList = new ArrayList<Packet>(recvQue);
		recvQue.clear();
		return tempList;
	}
	
	public ArrayList<Packet> getOutstandingPackets(){
		ArrayList<Packet> tempList = new ArrayList<Packet>(outstandingPackets);
		outstandingPackets.clear();
		return tempList;
	}
	
	public ArrayList<Packet> getOutstandingPackets(int id){
		ArrayList<Packet> tempList = new ArrayList<Packet>();
		for(Packet p : outstandingPackets){
			if(p.getPacketId() == id){
				tempList.add(p);
			}
		}
		for(Packet p : tempList){
			outstandingPackets.remove(p);
		}
		return tempList;
	}
	
	public boolean readPacket(){
		try {
			if(br.ready()){
				recvQue.add(Packet.readPacket(br));
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sendPacket(){
		if(sendQue.size() > 0){
			sendQue.remove(0).writePacketData(pw);
			return true;
		}
		return false;
	}
	
	public void shutdownConnection(){
		try {
			br.close();
			pw.close();
			socket.close();
			readThread.terminate();
			writeThread.terminate();
			readThread.join();
			writeThread.join();
			System.out.println("Shutdown complete");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: Get rid of this later
	public Socket getSocket(){
		return socket;
	}
	
	public boolean getIsClosed(){
		new Packet02TestConnection().writePacketData(pw);
		return pw.checkError();
	}
	
	static boolean readNetworkPacket(TcpConnection tc){
		return tc.readPacket();
	}
	
	static boolean sendNetworkPacket(TcpConnection tc){
		return tc.sendPacket();
	}
	
}
