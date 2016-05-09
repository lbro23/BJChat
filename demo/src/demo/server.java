package demo;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server extends Thread {
	private ServerSocket sock;
	Socket ss;


		
	public server() {
		try {
			sock = new ServerSocket(4445);
			ss = sock.accept();
			
			this.start();
		}catch (Exception e) {
			
		}
	}

	public static void main(String[] args){
		server s = new server();
	}
	
	public void run(){
		try{
			
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
