import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client extends Thread{
	String name;
	boolean running = true;
	
	Socket socket;
	Scanner consoleInput = new Scanner(System.in);
	Scanner inputFromServer;
	PrintStream outputToServer;
	
	
	public Client(String name, String address, int port) {
		this.name = name;
		try {
			socket = new Socket(address, port);
			inputFromServer = new Scanner(socket.getInputStream());
			outputToServer = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection Established");
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				// print any response from server
				try{
					System.out.println(inputFromServer.nextLine());
				} catch(NoSuchElementException e) { }
				
				// send stuff to server
				try{
					outputToServer.println(consoleInput.nextLine());
					System.out.print("Message Sent");
				} catch(NoSuchElementException e) { }
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void kill() {
		running = false;
		try {
			socket.close();
			consoleInput.close();
			inputFromServer.close();
			outputToServer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// to accept the connection
		
		Thread t = new Thread() {
			public void run() {
				try {
				ServerSocket s = new ServerSocket(4455);
				s.accept();
				} catch (IOException e) { e.printStackTrace();}
			}
		};
		t.start();
		
		Client c = new Client("Client 1", null, 4455);
		c.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c.kill();
	}

}
