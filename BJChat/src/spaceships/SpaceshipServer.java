package spaceships;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class SpaceshipServer extends Thread {
	static final int DEFAULT_PORT = 4457;
	
	// Server Fields
	ServerSocket socket;
	boolean running;
	
	// Clients
	List<SocketSucker> clients;
	
	// Debug
	public PrintStream debug = System.out;
	
	
	
	public SpaceshipServer(int port) {
		try {
			socket = new ServerSocket(port);
		} catch(IOException e) {
			debug.println("Server Setup Failed!");
			
			return;
		}
		
		clients = new ArrayList<SocketSucker>();
		
		running = true;
		// this.start();
		debug.println("New Spaceships Server Created Successfully!");
		acceptNewUsers();
	}
	
	public SpaceshipServer() {
		this(DEFAULT_PORT);
	}
	
	/**
	 * Accepts new users
	 * BLOCKS THE THREAD THAT CALLS THIS FUNCTION
	 */
	public void acceptNewUsers() {
		SpaceshipServer me = this;
		try {
			while (running) {
				Socket newUser = socket.accept();
				Thread appendUser = new Thread() {
					public void run() {
						clients.add(new SocketSucker(newUser,me));
					}
				};
				appendUser.start();
			}
		} catch (SocketException e) {
			running = false;
		} catch (Exception e) {
			running = false;
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO EVERYTHING
	}
	
	public static void main(String[] args) {
		Thread server = new Thread() {
			public void run() {
				SpaceshipServer s = new SpaceshipServer();
			}
		};
		server.start();
		
		Thread client = new Thread() {
			public void run() {
				try { Thread.sleep(500); } catch(Exception e) {}
				SpaceshipClient c = new SpaceshipClient(InetAddress.getLoopbackAddress(), DEFAULT_PORT);
			}
		};
		
		client.start();
	}

}

/**
 * Tracks a client, sucks on their socket
 */
class SocketSucker extends Thread {
	// Socket
	Socket socket;
	
	// Info
	String name;
	ObjectInputStream input;
	ObjectOutputStream output;
	ClientState lastState;
	
	boolean newState;
	boolean running;
	
	public SocketSucker(Socket sock, SpaceshipServer master) {
		try {
			this.socket = sock;
			output = new ObjectOutputStream(sock.getOutputStream());
			input = new ObjectInputStream(sock.getInputStream());
			lastState = (ClientState) input.readObject();
			name = lastState.getName();
		} catch (IOException | ClassNotFoundException e) {
			master.debug.println("There was a problem accepting the user");
			return;
		}
		master.debug.println(name + " has joined the server!");
		newState = false;
		running = true;
		this.start();
	}
	
	public void run() {
		while(running) {
			lastState = readObject();
			
			if(lastState != null) 
				newState = true;
			else
				break;
		}
	}
	
	public ClientState getLatestState() {
		return lastState;
	}
	
	public boolean hasNewState() {
		return newState;
	}
	
	/**
	 * Prints the GameState to this client
	 */
	public void sendState(GameState state) {
		try {
			output.writeObject(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the next object from this socket
	 * BLOCKS UNTIL THIS OBJECT IS FOUND
	 */
	private ClientState readObject() {
		try {
			return (ClientState)input.readObject();
		} catch(SocketException e) {
			running = false;
			return null;
		} catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
