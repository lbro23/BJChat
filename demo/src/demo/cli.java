package demo;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class cli {

	public static void main(String[] args){
		try{
		Scanner sc = new Scanner(System.in);
		Socket sock = new Socket("10.20.0.231", 4445);
		Scanner sc1 = new Scanner(sock.getInputStream());
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
