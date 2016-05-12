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
	
	
	public Client(int port, ClientGui gui) {
		InetAddress serverAddress = null;
		// get address
		recentInput = "";
		while(socket == null) {
			try {
				gui.println("Type desired Server Address, then press ENTER");
				serverAddress = InetAddress.getByName(userInputNextLine());
				socket = new Socket(serverAddress, port);
				toServer = new PrintStream(socket.getOutputStream());
				fromServer = new Scanner(socket.getInputStream());
			} catch(UnknownHostException e) {
				gui.println("Invalid Host Name! Please Try Again");
			} catch(ConnectException e) {
				gui.println("No Server Found! Is the address correct?");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		gui.println("Type desired Username, then press ENTER");
		userName = userInputNextLine();
		toServer.println(userName);
		running = true;
		this.start();
	}
	
	public Client() {
		this(defaultPort, null);
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
