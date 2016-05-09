package demo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	String name;
	Socket sock;
	Scanner systemInput, serverInput;
	
	public static void main(String[] args) {
		Client c = new Client(args[0], "localhost", 5454);
		c.start();
	}
	
	public Client(String name, String serverAddress, int port) {
		try { 
			this.name = name;
			systemInput = new Scanner(System.in);
			sock = new Socket(serverAddress, port);
			serverInput = new Scanner(sock.getInputStream());
			System.out.println("Type Messages, Then Press Enter to Send");
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Client(String name, Socket s) {
		try{ 
			this.name = name;
			sock = s;
			systemInput = new Scanner(System.in);
			serverInput = new Scanner(sock.getInputStream());
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				// get line from console
				String line = systemInput.nextLine();
				PrintStream p = new PrintStream(sock.getOutputStream());

				// send line from console to the server
				p.println(name + ": " + line);

				// print next line from server
				System.out.println(serverInput.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}
