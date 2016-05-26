package spaceships;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class SpaceshipClient extends Thread {
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	
	SpaceshipsGui gui;
	
	GameState lastState;
	
	PrintStream debug = System.out;
	
	boolean running;
	
	public SpaceshipClient(InetAddress serverAddress, int port) {
		try {
			debug.println("Attempting to Connect");
			socket = new Socket(serverAddress, port);
			
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
			//gui = new SpaceshipsGui();
			
			output.writeObject(new ClientState(null, "Leon")); // print initial client state
		} catch(Exception e) {
			e.printStackTrace();
		}
		running = true;
		this.start();
		debug.println("New Client Created Successfully");
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				lastState = (GameState)input.readObject();
				gui.drawGame(lastState);
			} catch(Exception e) {
				running = false;
				e.printStackTrace();
			}
		}
	}
	
	
	
}
