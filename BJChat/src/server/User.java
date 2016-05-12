package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class User {
	Socket socket;
	String name;
	int id;
	
	PrintStream outputStream;
	InputStream inputStream;
	
	
	public User(Socket socket, String name, int id) {
		this.socket = socket;
		this.name = name;
		this.id = id;

		try {
			outputStream = new PrintStream(socket.getOutputStream());
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PrintStream getOutputStream() { return outputStream; }
	public InputStream getInputStream() { return inputStream; }
	
	public String 
	
	
}
