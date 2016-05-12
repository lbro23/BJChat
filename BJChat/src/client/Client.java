package client;

import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread {
	static int defaultPort = 4445;
	String recentInput;
	Socket socket;
	Scanner fromServer;
	PrintStream toServer;
	String userName;
	ClientGui gui;
	
	boolean running;
	
	// PRECONDITION: s must be an open socket with the host
	public Client(ClientGui gui, Socket s, String name) {
		recentInput = "";
		socket = s;
		try {
			toServer = new PrintStream(socket.getOutputStream());
			fromServer = new Scanner(socket.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
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
			String line = userInputNextLine();
			toServer.println(userName + ": " + line);
		}
	}
	
	public void sendLine(String message) {
		recentInput = message;
	}
	
	public String userInputNextLine() {
		while(recentInput.equals("")) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String result = recentInput;
		recentInput = "";
		return result;
	}
}
