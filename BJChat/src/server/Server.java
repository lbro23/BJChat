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
	volatile ArrayList<ClientHandler> clients;
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
		refreshPing();
		String admin = getUsers(true);
		String muggle = getUsers(false);
		for(ClientHandler c: clients) {
			if(c.getUser().isAdmin()) {
				c.sayToClient(admin);
			} else {
				c.sayToClient(muggle);
			}
		}
		gui.updateUsers(admin);
	}
	
	public String getUsers(boolean administrator) {
		String result = "\\userupdate ";
		for(ClientHandler c: clients) {
			User user = c.getUser();
			result += user.getName();
			if(administrator) {
				
				result += " (" + user.getHostName() + ")";
				if(user.getMostRecentPing() == 9999) {
					result += ":: DC";
				} else {
					result += ":: " + user.getMostRecentPing();
				}
			}
			result += "//";
		}
		return result;
	}
	
	public void sendCommand(String cmd) {
		if(!(cmd.contains("\\"))) {
			sayToAllClients("<SERVER> " + cmd);
		} else {
			executeCommand(cmd.substring(1).split(" "));
		}
	}
	
	public void refreshPing() {
		for(ClientHandler c: clients) {
			c.updatePing();
		}
	}
	
	public void executeCommand(String[] cmd) {
		if (eq(cmd[0], "pingall") || eq(cmd[0], "updateusers")) {
			refreshPing();
			updateUsers();
			gui.println("Updating Users: Pinging All Clients");
		} else if (eq(cmd[0], "kill")) {
			ClientHandler u = findByName(cmd[1]);
			u.sayToClient("\\kill");
		} else if (eq(cmd[0], "kick")) {
			ClientHandler c = findByName(cmd[1]);
			if (c != null) {

				String message = "";
				for (int i = 2; i < cmd.length; i++) {
					message += cmd[i] + " ";
				}
				c.sayToClient("\\kick " + message);
				sayToAllClients("<SERVER> has kicked " + cmd[1]);
				sayToConsole(cmd[1] + " kicked (" + message + ")");
				removeUser(c);
			} else {
				sayToConsole("Invalid Username!");
			}
		} else if (eq(cmd[0], "setpassword")) {
			if (cmd.length > 2) {
				sayToConsole("Incorrect Command Format: Try \\setpassword NEWPASSWORD");
			} else {
				setAdminPassword(cmd[1]);
				sayToConsole("Password Set");
			}
		} else {
			gui.println("Unrecognized Command! Type \\help for suggestions");
		}
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
	
	public String getAdminPassword(){return adminPassword;}
	
	public ClientHandler findByName(String id) {
		for(ClientHandler c: clients) {
			if(c.getUser().getName().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Equals to ignore case
	 */
	public boolean eq(String s1, String s2) {
		return s1.compareToIgnoreCase(s2) == 0;
	}
}
