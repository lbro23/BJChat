package demo;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class cli extends Thread {
	Socket sock;
	Scanner sc;
	Scanner sc1;
	
	public static void main(String[] args) {
		cli c = new cli();
		c.start();
	}
	
	public cli() {
		try { 
			sc = new Scanner(System.in);
			sock = new Socket("10.20.0.231", 4445);
			sc1 = new Scanner(sock.getInputStream());
		} catch (Exception e) {
			
		}
	}

	public void run() {
		try{
			System.out.println("type any number");
			int number = sc.nextInt();
			PrintStream p = new PrintStream(sock.getOutputStream());
			
			p.println(number);
			System.out.println(sc1.nextInt());
		
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
