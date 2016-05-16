package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	static ArrayList<User> allUsers;
	
	Socket socket;
	volatile String name;
	int id;
	boolean administrator = false;
	volatile int ping;
	String hostName;
	
	PrintStream outputToClient;
	Scanner inputFromClient;
	
	
	public User(Socket socket, String name, Scanner input, int id) {
		// first time initialization
		if(allUsers == null) {
			allUsers = new ArrayList<User>();
		}
		this.inputFromClient = input;
		this.socket = socket;
		hostName = socket.getInetAddress().getHostName();
		this.name = name;
		this.id = id;

		try {
			outputToClient = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		allUsers.add(this);
	}
	
	/**
	 * close this user and all associated connections
	 */
	public void close() {
		allUsers.remove(this);
		try {
			outputToClient.close();
			inputFromClient.close();
			socket.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public PrintStream getOutputStream() { return outputToClient; }
	public Scanner getInput() { return inputFromClient; }
	
	public String getName() { return name; }
	public String getHostName() { return hostName; }
	public int getID() { return id; }
	public Socket getSocket() { return socket; }
	public boolean isAdmin() { return administrator; };
	public int getMostRecentPing() { return ping; }
	
	public void makeAdmin() { administrator = true; };
	public void revokeAdmin() { administrator = false; };
	public void changeName(String name) { this.name = name; }
	public void setMostRecentPing(int ping) { this.ping = ping; }
	
	/**
	 * Find the user with the given id
	 * @return - the user with the given id
	 * 			or null if no such user exists
	 */
	public static User findUser(int id) {
		for(User u: allUsers) {
			if(u.getID() == id) {
				return u;
			}
		}
		return null;
	}
	
	public static String findNameById(int id) {
		return findUser(id).getName();
	}
	
	
}
