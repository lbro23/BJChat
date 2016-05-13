package server;

import java.util.NoSuchElementException;

/**
 * This class will listen to the input stream from this client and be responsible for
 * soliciting a response from the server to this client
 * @author Leon
 *
 */
public class ClientHandler implements Runnable {
	User user;
	Server server;
	
	boolean running; // used to stop infinite loop
	
	public ClientHandler(Server server, User user) {
		running = true;
		this.user = user;
		this.server = server;
	}

	/**
	 * Watch the input from this client, and handle
	 * it accordingly
	 */
	@Override
	public void run() {
		try{
			while(running) {
				String message = user.getInput().nextLine();
				if(message.contains("\\")) {
					String[] cmd = message.substring(1).split(" ");
					server.sayToConsole("[Command Received] " + user.getName() + ": " + cmd[0]);
					executeCommand(cmd);
				} else {
					server.sayToAllClients(message);
				}
			}
		} catch (NoSuchElementException e) {}
	}
	
	/**
	 * Prints this message to the client's output stream
	 * @param message - message to be printed
	 */
	public void sayToClient(String message) {
		user.getOutputStream().println(message);
	}
	
	/**
	 * Closes this client, and all associated connections
	 */
	public void close() {
		running = false;
		user.close();
		server.removeUser(this);
		server.updateUsers();
	}
	
	public void executeCommand(String[] cmd) {
		if(eq(cmd[0], "disconnect")) {
			server.sayToAllClients(user.getName() + " has disconnected from the server");
			close();
		} else if(eq(cmd[0], "ping")) {
			sayToClient("\\pingresponse");
		} else if(eq(cmd[0], "changename")) {
			String old = user.getName();
			user.changeName(cmd[1]);
			server.sayToAllClients(old + " has changed their name to " + cmd[1]);
		}
	}
	
	/**
	 * Equals to ignore case
	 */
	public boolean eq(String s1, String s2) {
		return s1.compareToIgnoreCase(s2) == 0;
	}
	
	public User getUser() {
		return user;
	}
	

}
