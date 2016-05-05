package demo;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

	public static void main(String[] args){
		try{
		ServerSocket sock = new ServerSocket(4445);
		Socket ss = sock.accept();
		Scanner sc = new Scanner(ss.getInputStream());
		int number = sc.nextInt();
		
		number*=2;
		
		PrintStream p = new PrintStream(ss.getOutputStream());
		p.println(number);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
