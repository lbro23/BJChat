package demo;

import java.awt.List;
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server extends Thread {
	ServerSocket sock;
	Socket client;
	boolean running = true;


		
	public Server(int port) {
		try{ 
			sock = new ServerSocket(port);
			client = sock.accept();
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("New Server Created!");
		
	}
	
	public void run() {
		System.out.println("Server Running!");
		while (running) {
			try {
				// read input line
				Scanner sc = new Scanner(client.getInputStream());
				String line = sc.nextLine();
				if (line != null) {
					System.out.println("Server: " + line);

					// write to output
					PrintStream p = new PrintStream(client.getOutputStream());
					p.println(line);
					sc.close();
				}
			} catch (Exception e) {
				if (!(e instanceof NoSuchElementException)) {
					e.printStackTrace();
				}
			}
		}
		
		// dont create memory leaks
		try{
			sock.close();
			client.close();
		}catch(Exception e) {e.printStackTrace();}
	}
}
