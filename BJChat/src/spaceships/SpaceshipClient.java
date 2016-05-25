package spaceships;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SpaceshipClient extends Thread {
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	
	SpaceshipsGui gui;
	
	GameState lastState;
	
	boolean running;
	
	public SpaceshipClient(InetAddress serverAddress, int port) {
		try {
			socket = new Socket(serverAddress, port);
			
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
			gui = new SpaceshipsGui();
		} catch(Exception e) {
			e.printStackTrace();
		}
		running = true;
		this.start();
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
