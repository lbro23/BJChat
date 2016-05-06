package demo;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server extends Thread {
	

	public static void main(String[] args){
		server s = new server();
		s.start();
	}
	public void run(){
		try{
			ServerSocket sock = new ServerSocket(4445);
			Socket ss = sock.accept();
			Scanner sc = new Scanner(ss.getInputStream());
			int number = sc.nextInt();
			
			number*=3;
			System.out.println(number);
			
			PrintStream p = new PrintStream(ss.getOutputStream());
			p.println(number);
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
