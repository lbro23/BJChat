package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{
	static final int defaultPort = 4445;
	int currentId = 1;
	ServerSocket serverSocket;
	ArrayList<ClientHandler> clients;
	ServerGui gui;
	boolean running;
	String adminPassword = "default";
	// hello
	
	public Server(int port, ServerGui gui) {
		try {
			// create socket
			serverSocket = new ServerSocket(port);
			clients = new ArrayList<ClientHandler>();
			running = true;
			this.gui = gui;
			gui.println("New Server Created Successfully");
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Server() {
		this(defaultPort, null);
	}
	
	/**
	 * Handle user input from console
	 */
	@Override
	public void run() {
		try{ 
			gui.println("Awaiting Users..." );
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
				
				gui.println(name + " joined from " + newSocket.getInetAddress().getHostName() + " with ID " + currentId++);
				sayToAllClients(name + " has joined the server");
				updateUsers();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Say the message to all clients
	 */
	public void sayToAllClients(String message) {
		gui.println(message);
		for(ClientHandler c: clients) {
			c.sayToClient(message);
		}
	}
	
	public void updateUsers() {
		String admin = getUsers(true);
		String muggle = getUsers(false);
		for(ClientHandler c: clients) {
			if(c.getUser().isAdmin()) {
				c.sayToClient(admin);
			} else {
				c.sayToClient(muggle);
			}
		}
	}
	
	public String getUsers(boolean administrator) {
		String result = "\\userupdate ";
		for(ClientHandler c: clients) {
			User user = c.getUser();
			result += user.getName();
			if(administrator) {
				result += " (" + user.getHostName() + ") ";
				result += "Ping: " + user.getMostRecentPing();
			}
			result += "|";
		}
		return result;
	}
	
	public void removeUser(ClientHandler c) {
		clients.remove(c);
	}
	
	public void sayToConsole(String message) {
		gui.println(message);
	}
	
	public void setAdminPassword(String message) {
		adminPassword = message;
	}
}
