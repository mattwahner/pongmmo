package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Test implements Runnable{

	private boolean running = false;
	private static PrintWriter out;
	private static BufferedReader in;

	public static void main(String[] args){
		Socket echoSocket;
		try {
			echoSocket = new Socket("127.0.0.1", 7777);
			out = new PrintWriter(echoSocket.getOutputStream(), false);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println("Connection established");
			new Test().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(){
		running = true;
		new Thread(this).run();
	}

	public void run() {
		String userInput;
		while(running){
			try {
				if((userInput = in.readLine()) != null){
					out.println(userInput);
					out.flush();
					System.out.println("echo: " + userInput);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}