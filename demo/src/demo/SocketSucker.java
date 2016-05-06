package demo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketSucker extends Thread {
	public static void main(String[] args) {
		new SocketSucker();
	}
	
	public SocketSucker() {
		this.start();
	}
	
	public void run() {
		try { 
			Socket sock = new Socket("10.20.0.231", 4445);
			Scanner sc1 = new Scanner(sock.getInputStream());
			System.out.println(sc1.nextInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
