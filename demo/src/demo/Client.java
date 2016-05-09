package demo;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	String name;
	Socket sock;
	Scanner systemInput, serverInput;
	
	public static void main(String[] args) {
		Client c = new Client(args[0]);
		c.start();
	}
	
	public Client(String s) {
		try { 
			systemInput = new Scanner(System.in);
			sock = new Socket("10.20.0.231", 4445);
			serverInput = new Scanner(sock.getInputStream());
			System.out.println("Type Messages, Then Press Enter to Send");
		} catch (Exception e) {
			
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
		}

	}
}
