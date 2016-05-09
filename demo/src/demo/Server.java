package demo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server extends Thread {
	ServerSocket sock;
	Socket ss;


		
	public Server() {
		try {
			sock = new ServerSocket(4446);
			ss = sock.accept();
			
			this.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		Server s = new Server();
	}
	
	public void run() {
		try {
			while (true) {
				try{
					// read input line
					Scanner sc = new Scanner(ss.getInputStream());
					String line = sc.nextLine();
	
					// write to output
					PrintStream p = new PrintStream(ss.getOutputStream());
					p.println(line + "10");
				} catch (NoSuchElementException e) {
					// continue
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{ 
				sock.close();
				ss.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public Socket getSocket() {
		return ss;
	}
}
