package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{
	static final int defaultPort = 4445;
	int currentId = 1;
	ServerSocket serverSocket;
	ArrayList<ClientHandler> clients;
	Scanner input;
	boolean running;
	
	
	public Server(int port) {
		try {
			// create socket
			serverSocket = new ServerSocket(port);
			clients = new ArrayList<ClientHandler>();
			running = true;
			System.out.println("New Server Created Successfully");
			//this.start();
			//input = new Scanner(System.in);
			
			// accept users
			while(running) {
				// connect to user, gather information
				Socket newSocket = serverSocket.accept();
				Scanner newInput = new Scanner(newSocket.getInputStream());
				String name = newInput.nextLine();
				
				// create User, create clientHandler
				User newUser = new User(newSocket, name, newInput, currentId);
				ClientHandler handler = new ClientHandler(this, newUser);
				Thread newThread = new Thread(handler);
				clients.add(handler);
				newThread.start();
				
				System.out.println(name + " joined from " + newSocket.getInetAddress().getHostName() + " with ID " + currentId++);
				sayToAllClients(name + " has joined the server");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Server() {
		this(defaultPort);
	}
	
	/**
	 * Handle user input from console
	 */
	@Override
	public void run() {
		while(running) {
			String message = input.nextLine();
			sayToAllClients("[Server]: " + message);
		}
	}
	
	/**
	 * Say the message to all clients
	 */
	public void sayToAllClients(String message) {
		for(ClientHandler c: clients) {
			c.sayToClient(message);
		}
	} // TODO ADD SOCKET CLOSED HANDLING AT ALL THREADS
	
	/**
	 * Close this server and all associated connections
	 */
	public void close() {
		try {
			for(ClientHandler c: clients) {
				c.close();
			}
			running = false;
			serverSocket.close();
			input.close();
		} catch (SocketException e) {
			// Swallow
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread t = new Thread() { 
			public void run() {
				Server s = new Server();
			}
		};
		t.start();
		
		client.Client c = new client.Client();
	}
}
