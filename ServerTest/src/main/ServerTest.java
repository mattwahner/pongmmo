package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest implements Runnable{
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	private boolean running = false;
	
	public static void main(String[] args){
		try {
			ServerSocket serverSocket = new ServerSocket(7777);
			System.out.println("Server started");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connection accepted");
			out = new PrintWriter(clientSocket.getOutputStream(), false);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Connection established");
			out.println("test");
			out.flush();
			System.out.println("First packet sent");
			new ServerTest().start();
			System.out.println("Exited while");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Exit");
	}
	
	public void start(){
		running = true;
		new Thread(this).run();
	}

	public void run() {
		while(running){
			String inLine;
			try {
				System.out.println("waiting for recv");
				if((inLine = in.readLine()) != null){
					System.out.println("Recieved: " + inLine);
				}
				System.out.println("recv complete");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
