package demo;

import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Thread {
	String name;
	Socket sock;
	Scanner systemInput, serverInput;
	PrintStream outputStream;
	boolean running = true;
	
	public Client(String name, String serverAddress, int port) {
		try { 
			this.name = name;
			sock = new Socket(serverAddress, port);
			
			systemInput = new Scanner(System.in);
			serverInput = new Scanner(sock.getInputStream());
			
			outputStream = new PrintStream(sock.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Type Messages, Then Press Enter to Send");
	}

	public void run() {
		int i = 0;
		while (running) {
			try {
//				// get line from console
//				String line = systemInput.nextLine();
//
//				// send line from console to the server
//				p.println(name + ": " + line);
//
//				// print next line from server
//
//				System.out.println(serverInput.nextLine());
				outputStream.println(i);
				sleep(50);
			} catch (Exception e) {
				if(!(e instanceof NoSuchElementException)) {
					e.printStackTrace();
				}
			}
		}
		
		// dont create memory leaks
		try{
			sock.close();
			systemInput.close();
			serverInput.close();
		}catch(Exception e) {e.printStackTrace();}
	}
}
