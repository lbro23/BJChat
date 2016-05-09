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


		
	public Server() {
		try {
			sock = new ServerSocket(4445);
		} catch(Exception e){
			e.printStackTrace();
		}
		try {
			client = sock.accept();
		} catch(IOException e) {
			e.printStackTrace();
		}
			
		this.start();
	}
	
	public void run() {
		try {
			while (true) {
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

				} catch (NoSuchElementException e) {
					// continue
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{ 
				sock.close();
				client.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}
