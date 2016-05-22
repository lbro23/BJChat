package client;

import java.awt.Color;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.text.html.HTML;
public class Client extends Thread {
	ArrayList<DirectMessageWindow> dms;
	static int defaultPort = 4445;
	public boolean dead;
	Socket socket;
	Scanner fromServer;
	PrintStream toServer;
	String userName;
	ClientGui gui;
	
	String input;
	boolean newInput; // hi
	boolean running;
	
	// For Ping
	final int pingTimeout = 10000; // 3 seconds
	boolean awaitingPingResponse;
	long pingStartTime;
	
	
	// PRECONDITION: s must be an open socket with the host
	public Client(ClientGui gui, Socket s, String name) {
		dms = new ArrayList<DirectMessageWindow>();
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
				toServer.println("[" + userName + "]: " + input);
				input = null;
				newInput = false;
			}
			
			// check ping timeout
			if(awaitingPingResponse) {
				int timeElapsed = (int)(System.currentTimeMillis() - pingStartTime);
				if(timeElapsed > pingTimeout) {
					gui.println("Timed Out From Server: Check network connection and server status");
					awaitingPingResponse = false;
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
				}
				toServer.println("\\ping " + timeElapsed);
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
		if(!user) {
			if(rawCommand.charAt(0) == '\\') {
				String[] cmd = rawCommand.substring(1).split(" ");
				executeCommand(cmd, user, rawCommand);
				return false;
			} else {
				return true;
			}
		} else if(rawCommand.contains("\\")) {
			if(rawCommand.charAt(0) == '\\' && !rawCommand.substring(1).contains("\\")) { 
				String[] cmd = rawCommand.substring(1).split(" ");
				executeCommand(cmd, user, rawCommand);
				return false;
			} else {
				gui.println("Incorrectly Formatted Command! Use \\COMMAND followed by PARAMETERS");
				return true;
			}
		}
		return true;
	}
	
	public void executeCommand(String[] cmd, boolean user, String rawCommand) {
		if(eq(cmd[0], "kill")) {
			try {
				closeAllDM();
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
		}else if (eq(cmd[0], "kick")){
			if(user) {
				toServer.println(rawCommand);
			} else {
				try{
					String message = rawCommand.substring(6);
					message = message.trim();
					closeAllDM();
					toServer.println("\\dc");
					fromServer.close();
					toServer.close();
					socket.close();
					
					gui.println("Disconnected");
					dead = true;
					gui.kicked(message);
					gui.closeWindow();
				}catch(Exception e){ e.printStackTrace(); }
			}
		}
		else if(eq(cmd[0], "ping")) {
			pingServer();
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
				if(ClientGui.isValidName(cmd[1])) {
					userName = cmd[1];
					toServer.println("\\" + cmd[0] + " " + cmd[1]);
				} else {
					gui.println("Incorrect Name Format! Must be letters and digits between 2 and 10 Characters");
				}
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
			if(!user) {
				updateUsers(rawCommand.substring(12));
			}
		} else if(eq(cmd[0], "dm")) { // COMMAND FORMAT "\\dm TO FROM" 
			if(user) {
				if(cmd.length != 2) gui.println("Incorrect Command Format! Try \\dm USERNAME");
				toServer.println("\\dm " + cmd[1] + " " + userName); // request to start DM
				gui.println("DM Request Sent, waiting for " + cmd[1] + " to accept.");
			} else {
				boolean response = gui.booleanMessage(cmd[2] + " has requested a Direct Message session.\n Do you accept?");
				if(response) dms.add(new DirectMessageWindow(cmd[2], userName, this));
				toServer.println("\\dmresponse " + cmd[2] + " " + cmd[1] + " " +  response);
			}
		} else if(eq(cmd[0], "dmresponse")) { // COMMAND FORMAT "\\dmresponse true/false TO FROM"
			if(user) {
				gui.println("Incorrect Command Format! Try \\dm USERNAME");
			} else {
				if(cmd[3].equals("true")) {
					gui.println(cmd[2] + " has accepted your DM request! Opening DM Window.");
					dms.add(new DirectMessageWindow(cmd[2], userName, this));
				} else {
					gui.println(cmd[2] + " has rejected your DM request!" );
				}
			}
		} else if(eq(cmd[0], "dmmessage") || eq(cmd[0], "whisper")) { // COMMAND FORMAT "\\dmmessage DESTINATION MESSAGE"
			if(user) {
				
				String message = "\\dmmessage " + cmd[1] + " " + userName;
				for(int i = 2; i < cmd.length; i++) { message += " " + cmd[i]; }
				toServer.println(message);
			} else {
				String message = "";
				for(int i = 3; i < cmd.length; i++) {
					message+= cmd[i] + " ";
				}
				DirectMessageWindow dmWindow = findDMByName(cmd[2]);
				if(dmWindow != null) {
					dmWindow.sendLine(cmd[2] + ": " + message);
				} else {
					gui.println("<WHISPER> " + cmd[2] + ": " + message);
				}
			}
		} else if(eq(cmd[0], "dmclose" ) ) {
			if(user) {
				toServer.println(rawCommand);
			} else {
				findDMByName(cmd[2]).close();
			}
		} else if(eq(cmd[0], "dmcloseall")) {
			closeAllDM();
		} else if(eq(cmd[0], "checkers")) { // COMMAND FORMAT \\checkers TO FROM 
			if(user) {
				String message = "\\checkers " + cmd[1] + " " + userName;
				toServer.println(message);
			} else {
				boolean response = gui.booleanMessage(cmd[2] + " has requested to play Checkers with you.\nWould you like to accept?");
				if(response) {
					toServer.println("\\checkersstart " + cmd[2] + " " + userName);
				} else {
					toServer.println("\\checkersdeclined " + cmd[2] + " " + userName);
				}
			}
		} else if(eq(cmd[0], "checkersdeclined")) {
			if(user) {
				// Nothing
			} else {
				gui.println("Your request to play Checkers with " + cmd[2] + " has been declined. Consider getting better friends");
			}
		} else if(eq(cmd[0], "checkersstart")) { // COMMAND FORMAT //checkersstart TO FROM BOOL(P1 or P2) PORT#
			gui.println("Your checkers game with " + cmd[2] + " is beginning! Good luck!");
			startCheckers(Boolean.parseBoolean(cmd[3]), Integer.parseInt(cmd[4]));
		}
		else {
			if(user) {
				toServer.println(rawCommand);
			}
		}
		gui.println(rawCommand);
	}
	
	public void updateUsers(String names) {
		gui.updateUserPane(names.split("//"));
	}
 
	public void pingServer() {
		toServer.println("\\ping");
		pingStartTime = System.currentTimeMillis();
		awaitingPingResponse =  true;
	}
	
	public DirectMessageWindow findDMByName(String name) {
		for(DirectMessageWindow w: dms) {
			if(w.getName().equals(name)) {
				return w;
			}
		}
		return null;
	}
	
	public void closeAllDM() {
		for(int i = 0; i < dms.size(); i++) {
			dms.remove(i).exit();
		}
	}
	
	public void startCheckers(boolean starter, int port) {
		if (starter) {
			new checkers.Player(Color.BLACK, socket.getInetAddress(), port, 1);
		} else {
			new checkers.Player(Color.RED, socket.getInetAddress(), port, 2);
		}
	}
	
	public void removeDM(DirectMessageWindow d) { dms.remove(d); }
	
	/**
	 * Equals to ignore case
	 */
	public boolean eq(String s1, String s2) {
		return s1.compareToIgnoreCase(s2) == 0;
	}
}
