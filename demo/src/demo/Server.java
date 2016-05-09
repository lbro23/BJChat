package demo;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
	private ServerSocket sock;
	Socket ss;


		
	public Server() {
		try {
			sock = new ServerSocket(4445);
			ss = sock.accept();
			
			this.start();
		}catch (Exception e) {
			
		}
	}

	public static void main(String[] args){
		Server s = new Server();
	}
	
	public void run() {
		try {
			while (true) {
				// read input line
				Scanner sc = new Scanner(ss.getInputStream());
				String line = sc.nextLine();

				// write to output
				PrintStream p = new PrintStream(ss.getOutputStream());
				p.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
