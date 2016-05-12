package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	static ArrayList<User> allUsers;
	
	Socket socket;
	String name;
	int id;
	
	PrintStream outputToClient;
	Scanner inputFromClient;
	
	
	public User(Socket socket, String name, int id) {
		// first time initialization
		if(allUsers == null) {
			allUsers = new ArrayList<User>();
		}
		this.socket = socket;
		this.name = name;
		this.id = id;

		try {
			outputToClient = new PrintStream(socket.getOutputStream());
			inputFromClient = new Scanner(socket.getInputStream());
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
	public Scanner getInputStream() { return inputFromClient; }
	
	public String getName() { return name; }
	public int getID() { return id; }
	public Socket getSocket() { return socket; }
	
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
	
	
}
