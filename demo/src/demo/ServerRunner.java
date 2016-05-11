package demo;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerRunner implements Runnable {

	Socket sock;
	private Scanner input;
	private PrintWriter output;
	private boolean running;
	
	public ServerRunner(Socket sock){
		this.sock = sock;
	}
	public void run() {
		try{
			input = new Scanner(sock.getInputStream());
			output = new PrintWriter(sock.getOutputStream());
			
			running = true;
				Thread checker = new Thread(){
					public void run() {
						while (running) {
							check();
						}
					}
				};
				
				Thread reader = new Thread(){
					public void run(){
					while(running){
					if(input.hasNext()){
						String message = input.nextLine();
						Server.say(sock, message);
					}	
						}
					}
				};
				checker.start();
				reader.start();
				
				
				
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void check() {
		if(sock.isInputShutdown()){
			System.out.println("code ran");
			Server.remove(sock);
			running = false;
			try{
			sock.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	

}
