package demo;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientStartup {
	final static int port = 4445;

	public static void main(String[] args0) {
		Socket sock = null;
		Scanner in = new Scanner(System.in);
		String ip;
		InetAddress add = null;
		while (sock == null) {
			System.out.println("Enter valid server IP");
			ip = in.nextLine();
			try {
				add = InetAddress.getByName(ip);
				sock = new Socket(add, port);
			} catch (Exception e) {
				System.out.println("Unable to connect to that ip");
			}
		}
		System.out.println("Enter Username");
		String name = in.nextLine();
		try{
		PrintWriter output = new PrintWriter(sock.getOutputStream());
		output.println(name);
		output.flush();
		}catch(Exception e){
			System.out.println("you done messed up");
		}
		System.out.println("Connected to " + add.getHostName());
		Client user = new Client(name, sock);
		Thread x = new Thread(user);
		x.start();
	}
	
}
