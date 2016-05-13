package client;

import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Thread {
	static int defaultPort = 4445;
	public boolean dead;
	Socket socket;
	Scanner fromServer;
	PrintStream toServer;
	String userName;
	ClientGui gui;
	
	String input;
	boolean newInput;
	boolean running;
	
	// For Ping
	final int pingTimeout = 10000; // 3 seconds
	boolean awaitingPingResponse;
	long pingStartTime;
	
	
	// PRECONDITION: s must be an open socket with the host
	public Client(ClientGui gui, Socket s, String name) {
		running = true;
		socket = s;
		try {
			toServer = new PrintStream(socket.getOutputStream());
			fromServer = new Scanner(socket.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.gui = gui;
		this.userName = name;
		toServer.println(userName);
		this.start();
	}
	
	@Override
	public void run() {
		Thread t = new Thread() {
			public void run() {
				try {
					while(running) {
						// check and execute any commands
						String nextLine = fromServer.nextLine();
						if(handleCommand(nextLine, false)) {
							gui.println(nextLine);
						}
					}
				} catch(IllegalStateException e) {
				} catch(NoSuchElementException e) {}
			}
		};
		t.start();
		
		while(running) {
			if(newInput) {
				// check and execute any commands
				toServer.println(userName + ": " + input);
				input = null;
				newInput = false;
			}
			
			// check ping timeout
			if(awaitingPingResponse) {
				int timeElapsed = (int)(System.currentTimeMillis() - pingStartTime);
				if(timeElapsed > pingTimeout) {
					gui.println("Timed Out From Server: Check network connection and server status");
					awaitingPingResponse = false;
				}
			}
			
			try{ Thread.sleep(1); }
			catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public void sendLine(String message) {
		if(handleCommand(message, true)) {
			input = message;
			newInput = true;
		}
	}
	
	/**
	 * Determines whether or not the string contains a command
	 * and takes the appropriate action
	 */
	public boolean handleCommand(String rawCommand, boolean user) {
		if(rawCommand.contains("\\")) {
			if(rawCommand.charAt(0) == '\\' && !rawCommand.substring(1).contains("\\")) {
				String[] cmd = rawCommand.substring(1).split(" ");
				executeCommand(cmd, user);
				return false;
			} else {
				gui.println("ERROR: Incorrectly Formatted Command: Use \\ followed by command and parameters, separated by spaces");
				return true;
			}
		}
		return true;
	}
	
	public void executeCommand(String[] cmd, boolean user) {
		if(eq(cmd[0], "kill")) {
			try {
				toServer.println("\\disconnect");
				fromServer.close();
				toServer.close();
				socket.close();
				
				gui.println("Disconnected");
				dead = true;
				gui.closeWindow();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(eq(cmd[0], "ping")) {
			if(user) {
				pingServer();
			} else {
				toServer.println("\\pingresponse");
			}
		} else if(eq(cmd[0], "pingresponse")) {
			if(user) {
				gui.println("Incorrect Command: Did you mean \\ping?");
			} else {
				awaitingPingResponse = false;
				int timeElapsed = (int)(System.currentTimeMillis() - pingStartTime);
				gui.println("Ping " + socket.getInetAddress().getHostName() + ", Result: " + timeElapsed);
			}
		} else if(eq(cmd[0], "changename")) {
			if(cmd.length == 2) {
				userName = cmd[1];
				toServer.println("\\" + cmd[0] + " " + cmd[1]);
			} else {
				gui.println("Incorrect Command Format: Try \\changename NEWNAME");
			}
		} else if(eq(cmd[0], "clean")) {
			if(user) {
				gui.cleanConsole("Console Cleaned" );
			} else {
				gui.cleanConsole("Console Cleaned: Server Command");
			}
		} else if(eq(cmd[0], "userupdate")){
			updateUsers(cmd[1]);
		}
	}
	
	public void updateUsers(String names) {
		gui.updateUserPane(names.split("|"));
	}
 
	public void pingServer() {
		toServer.println("\\ping");
		pingStartTime = System.currentTimeMillis();
		awaitingPingResponse =  true;
	}
	
	/**
	 * Equals to ignore case
	 */
	public boolean eq(String s1, String s2) {
		return s1.compareToIgnoreCase(s2) == 0;
	}
}
