package spaceships;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SpaceshipClient {
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	
	public SpaceshipClient(InetAddress serverAddress, int port) {
		try {
			socket = new Socket(serverAddress, port);
			
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch(Exception e) {
			
		}
	}
	
	
}
