package demo;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class cli extends Thread {
	Socket sock;
	Scanner sc;
	
	public static void main(String[] args) {
		cli c = new cli();
		c.start();
	}
	
	public cli() {
		try { 
			sc = new Scanner(System.in);
			sock = new Socket("10.20.0.231", 4445);
			System.out.println("Say hi");
		} catch (Exception e) {
			
		}
	}

	public void run() {
		try{
			while(true){
			Scanner sc1 = new Scanner(sock.getInputStream());
		
			String line = sc.nextLine();
			PrintStream p = new PrintStream(sock.getOutputStream());
			
			p.println(line);
			System.out.println(sc1.nextLine());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
