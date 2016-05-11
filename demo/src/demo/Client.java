package demo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Runnable  {
	String name;
	Socket sock;
	Scanner systemInput, serverInput;
	PrintStream outputStream;
	boolean running;
	
	
	public Client(String name, Socket sock) {
		try { 
			this.name = name;
			this.sock = sock;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Type Messages, Then Press Enter to Send");
	}

	public void run() {
		try {
			systemInput = new Scanner(System.in);
			serverInput = new Scanner(sock.getInputStream());
			
			outputStream = new PrintStream(sock.getOutputStream());
		} catch(IOException e) {e.printStackTrace(); }
		
		while (true) {
			try {
				System.out.print('p');
				Thread checkConsole = new Thread() {
					public void run() {
						while(true) {
							if(systemInput.hasNext()) {// get line from console
								String line = systemInput.nextLine();
								if(line.equals("/kill")) {kill();running = false; break;}
								// send line from console to the server
								outputStream.println(line);
							}	
						}
					}
				};
				
				Thread checkServer = new Thread() {
					public void run() {
						while(true) {
							if(serverInput.hasNext()) {
								// print next line from server
								System.out.println(serverInput.nextLine());
							}
							if(!running) {break;}
						}
					}
				};
				checkConsole.start();
				checkServer.start();
				

			} catch (Exception e) {
				if(!(e instanceof NoSuchElementException)) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void kill() {
		System.out.println("RIP");
		// dont create memory leaks
		try{
			sock.close();
			systemInput.close();
			serverInput.close();
		}catch(Exception e) {e.printStackTrace();}
	}
}
