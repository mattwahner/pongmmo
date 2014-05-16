package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTest {
	
	public static void main(String[] args){
		try {
			ServerSocket serverSocket = new ServerSocket(7777);
			System.out.println("Server started");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connection accepted");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String outputLine;
			outputLine = "Test";
			while(true){
				out.println(outputLine);
				System.out.println(in.readLine());
				Scanner scan = new Scanner(System.in);
				outputLine = scan.nextLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
