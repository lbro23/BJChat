package demo;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerRunner implements Runnable {

	Socket sock;
	private Scanner input;
	private PrintWriter output;
	
	public ServerRunner(Socket sock){
		this.sock = sock;
	}
	public void run() {
		try{
			input = new Scanner(sock.getInputStream());
			output = new PrintWriter(sock.getOutputStream());
			
			
				Thread checker = new Thread(){
					public void run() {
						while (true) {
							check();
						}
					}
				};
				
				Thread reader = new Thread(){
					public void run(){
					while(true){
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
		}finally{
			
		}
		
	}
	private void check() {
		if(!sock.isConnected() || sock.isClosed()){
			Server.remove(sock);
			try{
			sock.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	

}
