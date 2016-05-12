package client;

import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread {
	static int defaultPort = 4445;
	Socket socket;
	Scanner fromServer;
	PrintStream toServer;
	String userName;
	ClientGui gui;
	
	String input;
	boolean newInput;
	boolean running;
	
	// PRECONDITION: s must be an open socket with the host
	public Client(ClientGui gui, Socket s, String name) {
		running = true;
		socket = s;
		try {
			toServer = new PrintStream(socket.getOutputStream());
			fromServer = new Scanner(socket.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.gui = gui;
		this.userName = name;
		toServer.println(userName);
		this.start();
	}
	
	@Override
	public void run() {
		Thread t = new Thread() {
			public void run() {
				while(running) {
					gui.println(fromServer.nextLine());
				}
			}
		};
		t.start();
		
		while(running) {
			if(newInput) {
				toServer.println(userName + ": " + input);
				input = "";
				newInput = false;
			}
			
			try{ Thread.sleep(1); }
			catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public void sendLine(String message) {
		input = message;
		newInput = true;
	}
}
