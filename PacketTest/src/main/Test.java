package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Test {

	public static void main(String[] args){
		Socket echoSocket;
		PrintWriter out;
		BufferedReader in;
		try {
			echoSocket = new Socket("127.0.0.1", 7777);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while((userInput = stdIn.readLine()) != null){
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
