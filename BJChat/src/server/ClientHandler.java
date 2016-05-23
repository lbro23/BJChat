package server;

import java.util.NoSuchElementException;

/**
 * This class will listen to the input stream from this client and be responsible for
 * soliciting a response from the server to this client
 * @author Leon
 *
 */
public class ClientHandler implements Runnable {
	static final int PING_TIMEOUT = 9999; // hi
	User user;
	Server server;
	
	volatile boolean awaitingPingResponse;
	volatile long lastConnectTime;
	
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
					executeCommand(cmd, message);
				} else {
					server.sayToAllClients(message);
				}
				lastConnectTime = System.currentTimeMillis();
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
	// hello
	public void executeCommand(String[] cmd, String rawCommand) {
		if(eq(cmd[0], "disconnect")) {
			server.sayToAllClients(user.getName() + " has disconnected from the server");
			close();
		} else if(eq(cmd[0], "dc")) {
			close();
		} else if(eq(cmd[0], "ping")) {
			if(cmd.length > 1) {
				user.setMostRecentPing(Integer.parseInt(cmd[1]));
			} else {
				sayToClient("\\pingresponse");
			}
		} else if(eq(cmd[0], "changename")) {
			String old = user.getName();
			user.changeName(cmd[1]);
			server.sayToAllClients(old + " has changed their name to " + cmd[1]);
			server.updateUsers();
		} else if(eq(cmd[0], "pingresponse")) {
			// NOTHING
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
		} else if(eq(cmd[0], "dm") || eq(cmd[0], "dmresponse") || eq(cmd[0], "dmmessage") || eq(cmd[0], "dmclose")) {
			server.findByName(cmd[1]).sayToClient(rawCommand);
		} else if(eq(cmd[0], "checkers") || eq(cmd[0], "checkersdeclined")) {
			ClientHandler c = server.findByName(cmd[1]);
			if(c == null) {sayToClient("Invalid Username!"); return; }
			c.sayToClient(rawCommand);
		} else if(eq(cmd[0], "checkersstart")) {
			server.sayToConsole("New Checkers Game Created: " + cmd[1] + " vs. " + cmd[2]);
			int port = server.startCheckersGame(getUser().getSocket().getInetAddress(), 
					server.findByName(cmd[1]).getUser().getSocket().getInetAddress());
			sayToClient("\\checkersstart " + cmd[2] + " " + cmd[1] + " true " + port);
			server.findByName(cmd[1]).sayToClient("\\checkersstart " + cmd[1] + " " + cmd[2] + " false " + port);
		}
		else {
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
		user.setMostRecentPing(-1); // clears value
		sayToClient("\\ping");
	}
	
	public long getLastConnectTime() { return lastConnectTime; }
	

}
