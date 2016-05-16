package server;

import java.util.NoSuchElementException;

/**
 * This class will listen to the input stream from this client and be responsible for
 * soliciting a response from the server to this client
 * @author Leon
 *
 */
public class ClientHandler implements Runnable {
	static final int PING_TIMEOUT = 9999;
	User user;
	Server server;
	
	boolean awaitingPingResponse;
	long pingStartTime;
	
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
				if(awaitingPingResponse) {
					long timeElapsed = System.currentTimeMillis() - pingStartTime;
					if(timeElapsed > PING_TIMEOUT) { user.setMostRecentPing(9999); }
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
			server.updateUsers();
		} else if(eq(cmd[0], "pingresponse")) {
			if(awaitingPingResponse) {
				awaitingPingResponse = false;
				long timeElapsed = System.currentTimeMillis() - pingStartTime;
			}
		} else if(eq(cmd[0], "kick")) {
			if(getUser().isAdmin() ) {
				ClientHandler c = server.findByName(cmd[1]);
				if(c == null) { sayToClient("Invalid Name! Try again"); return; }
				String message = c.getUser().getName() + " has been kicked by " + getUser().getName();
				server.sayToAllClients(message);
				if(cmd.length > 2) {
					message = "";
					for(int i = 2; i < cmd.length; i++) { // create message
						message += cmd[i];
					}
				}
				server.kickUser(c, message);
			} else {
				sayToClient("Insufficient Permissions for this action");
			}
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
	
	public void updatePing() {
		pingStartTime = System.currentTimeMillis();
		awaitingPingResponse = true;
	}
	

}
