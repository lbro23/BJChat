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
			
			while(true){
				check();
			}
		}catch(Exception e){
			
		}
		
	}
	private void check() {
		if(!sock.isConnected()){
			Server.remove(sock);
		}
		
	}
	

}
