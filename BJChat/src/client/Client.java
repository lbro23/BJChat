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
	Scanner userInput;
	Scanner fromServer;
	PrintStream toServer;
	String userName;
	ClientGui gui;
	
	boolean running;
	
	
	public Client(int port, ClientGui gui) {
		InetAddress serverAddress = null;
		userInput = new Scanner(System.in);
		// get address
		while(socket == null) {
			try {
				System.out.println("Type desired Server Address, then press ENTER");
				serverAddress = InetAddress.getByName(userInput.nextLine());
				socket = new Socket(serverAddress, port);
				toServer = new PrintStream(socket.getOutputStream());
				fromServer = new Scanner(socket.getInputStream());
			} catch(UnknownHostException e) {
				System.out.println("Invalid Host Name! Please Try Again");
			} catch(ConnectException e) {
				System.out.println("No Server Found! Is the address correct?");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Type desired Username, then press ENTER");
		userName = userInput.nextLine();
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
					System.out.println(fromServer.nextLine());
				}
			}
		};
		t.start();
		
		while(running) {
			String line = userInput.nextLine();
			toServer.println(userName + ": " + line);
		}
	}
	
	public void sendLine(String message) {
		// TODO
	}
	
	public static void main(String[] args) {
		Client c = new Client();
	}
}
