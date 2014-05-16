package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	
	public static void main(String[] args){
		try {
			ServerSocket serverSocket = new ServerSocket(7777);
			System.out.println("Server started");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connection accepted");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Connection established");
			out.println("test");
			System.out.println("First packet sent");
			String inputLine;
			while((inputLine = in.readLine()) != null){
				System.out.println(inputLine);
				out.println("test");
				System.out.println("Packet sent");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
