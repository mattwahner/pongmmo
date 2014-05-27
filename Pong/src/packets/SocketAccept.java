package packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAccept implements Runnable {
	
	private PacketHandler packetHandler;
	private ServerSocket serverSocket;
	
	private boolean running = false;
	
	public SocketAccept(PacketHandler packetHandler, ServerSocket serverSocket){
		this.packetHandler = packetHandler;
		this.serverSocket = serverSocket;
	}
	
	public void start(){
		running = true;
		new Thread(this).run();
	}
	
	public void stop(){
		running = false;
	}
	
	public void run(){
		while(running){
			try {
				System.out.println("Accepting socket");
				Socket tempSocket = serverSocket.accept();
				packetHandler.addSocket(tempSocket);
				System.out.println("Socket accepted");
				packetHandler.addOut(new PrintWriter(tempSocket.getOutputStream(), true));
				packetHandler.addIn(new BufferedReader(new InputStreamReader(tempSocket.getInputStream())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
