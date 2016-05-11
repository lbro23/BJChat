package demo;

import java.awt.List;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server  {
	static final int port = 4445;
	static ArrayList<Socket> users = new ArrayList<Socket>();
	static ArrayList<String> names = new ArrayList<String>();
	
	public static void main(String[] args0){
		try
		{
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server created waiting for users...");
			
			while(true){
				Socket sock = server.accept();
				addUser(sock);
				
				System.out.println("New user from " + sock.getLocalAddress().getHostName() + " has connected");
			} 
			
		}catch(Exception e){
			System.out.println("Unable to make server");
			e.printStackTrace();
			
		}
	}

	private static void addUser(Socket sock) {
		try{
		users.add(sock);
		Scanner input = new Scanner(sock.getInputStream());
		String userName = input.nextLine();
		names.add(userName);
		
		for(int i= 0; i <users.size(); i++){
			Socket temp =  (users.get(i));
			PrintWriter output = new PrintWriter(temp.getOutputStream());
			output.print(userName + " has joined");
		}
		}catch(Exception e){
			System.out.println("Unable to connect User");
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
}

		
	
