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
				user.setMostRecentPing((int)(System.currentTimeMillis() - pingStartTime));
			}
		} else if(eq(cmd[0], "admin")){
			if(cmd.length != 2) {
				sayToClient("Incorrect Command Format: Try \\admin ADMINPASSWORD");
			}else if(eq(cmd[1], server.getAdminPassword())){
				user.makeAdmin();
				sayToClient("You are now a Server Administrator");
				server.sayToConsole(user.getName() + " is now an admin");
				server.updateUsers(); // send new user info
			}else{
				sayToClient("Incorrect Password");
			}
		} else if(eq(cmd[0], "kick")){
			if(user.isAdmin()){
				ClientHandler c = server.findByName(cmd[1]);
				String message = "";
				for(int i = 2; i < cmd.length; i++) {
					message += cmd[i] + " ";
				}
				if(c!= null){
					server.sayToAllClients(cmd[1] + " has been kicked by " + user.getName());
					server.sayToConsole(user.getName() + "'s Kick Message: " + message);
					c.sayToClient("\\kick " + message);
				}else{
					sayToClient("Invalid User: Try \\kick USERTOKICK");
				}
			}else{
				sayToClient("Insufficient Permission");
			}
		} else if(eq(cmd[0], "help")) {
			sayToClient("Not Yet Implemented");
		} else {
			sayToClient("Unrecognized Command! Try \\help");
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
