package server;
/**
 * This class will listen to the input stream from this client and be responsible for
 * soliciting a response from the server to this client
 * @author Leon Breaux
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
		while(running) {
			String message = user.getInput().nextLine();
			if(message.contains("\\")) {
				int slashLoc = message.indexOf('\\');
				int commandEnd =  message.indexOf(' ', slashLoc+1);
				String command;
				if(commandEnd == -1) {
					command = message.substring(slashLoc+1);
				} else {
					command = message.substring(slashLoc+1,commandEnd);
				}
				// execute command
				executeCommand(command);
				server.sayToAllClients("[COMMAND] CID " + user.getID() + ": " + command);
				// message has '\', its a command
			} else {
				server.sayToAllClients(message);
			}
		}
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
	}
	
	public void executeCommand(String command) {
		if(eq(command, "killserver")) {
			server.close();
		} else if(eq(command, "ping")) {
			sayToClient("\\pingresponse");
		} else if(eq(command, "disconnect") || eq(command, "exit")) {
			this.close();
			// TODO disconnect from server
		}
	}
	
	public boolean eq(String str1, String str2) {
		return str1.compareToIgnoreCase(str2) == 0;
	}
	
	

}
