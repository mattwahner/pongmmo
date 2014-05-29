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
	
	public ArrayList<Packet> getRecvList(boolean clear){
		if(clear){
			ArrayList<Packet> tempList = new ArrayList<Packet>(recvQue);
			recvQue.clear();
			return tempList;
		}else return recvQue;
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
			socket.close();
			br.close();
			pw.close();
			readThread.terminate();
			writeThread.terminate();
			readThread.join();
			writeThread.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getIsClosed(){
		return socket.isClosed();
	}
	
	static boolean readNetworkPacket(TcpConnection tc){
		return tc.readPacket();
	}
	
	static boolean sendNetworkPacket(TcpConnection tc){
		return tc.sendPacket();
	}
	
}
