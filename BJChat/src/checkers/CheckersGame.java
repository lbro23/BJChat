package checkers;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CheckersGame {
	int playersTurn;
	CheckerBoard board;
	ServerSocket serverSocket;
	
	//player 1 fields
	Socket p1;
	ObjectOutputStream p1Output;
	ObjectInputStream p1Input;
	CheckerBoard board1;
	boolean newBoard1;
	
	//player 2 fields
	Socket p2;
	ObjectOutputStream p2Output;
	ObjectInputStream p2Input;
	CheckerBoard board2;
	boolean newBoard2;
	
	boolean running;

	
	public CheckersGame(InetAddress player1, InetAddress player2, int port){
		try{
			serverSocket = new ServerSocket(port);
			
			// connect
			while(p1 == null || p2 == null) {
				Socket s = serverSocket.accept();
				if(s.getInetAddress().equals(player1) && p1 == null) {
					p1 = s;
				} else if(s.getInetAddress().equals(player2) && p2 == null) {
					p2 = s;
				} else {
					s = null;
				}
			}
			playersTurn = 1;
			running = true;

			p1Input = new ObjectInputStream(p1.getInputStream());
			p1Output = new ObjectOutputStream(p1.getOutputStream());
			
			p2Input = new ObjectInputStream(p2.getInputStream());
			p2Output = new ObjectOutputStream(p2.getOutputStream());
			
			board = new CheckerBoard();
			p1Output.writeObject(board);
			p2Output.writeObject(board);
			updateInput();
			this.run();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void updateInput() {
		try {
			Thread t1 = new Thread() {
				public void run() {
					while (running) {
						try {
							board1 = (CheckerBoard) p1Input.readObject();
							if (board1 == null) {
								close(); running = false; p2Output.writeObject(null);
								System.out.println("Null Received from P1, Sending to 2");
							} 
							newBoard1 = true;
						} catch (EOFException | SocketException e) {
							running = false;
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			Thread t2 = new Thread() {
				public void run() {
					while (running) {
						try {
							board2 = (CheckerBoard) p2Input.readObject();
							if (board2 == null) {
								close(); running = false; p1Output.writeObject(null);
								System.out.println("Null Received from P2, Sending to 1");
							}
							newBoard2 = true;
						} catch (EOFException | SocketException e) {
							running = false;
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
				t1.start();
				t2.start();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void run() {
		while(running){
			board = getMove(playersTurn);
			
			if(playersTurn == 1) playersTurn = 2; // change to other
			else playersTurn = 1;
			
			updateOtherPlayer(playersTurn);
		}
	}
	
	private void close() {
		try { 
			p1Input.close();
			p1Output.close();
			p2Input.close();
			p2Output.close();
			
			p1.close();
			p2.close();
			
			serverSocket.close();
		} catch(Exception e) {e.printStackTrace();}
	}

	private void updateOtherPlayer(int player) {
		try {
			if(player == 1) {
				p1Output.writeObject(board);
			} else {
				p2Output.writeObject(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private CheckerBoard getMove(int player) {
		try {
			if (player == 1) {
				while (!newBoard1) { Thread.sleep(3); }
				newBoard1 = false;
				return board1;
			} else {
				while (!newBoard2) { Thread.sleep(3); }
				newBoard2 = false;
				return board2;
			}
		}catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	public static void main(String[] args) {
		InetAddress local = InetAddress.getLoopbackAddress();
		Thread server = new Thread() {
			public void run() {
				CheckersGame game = new CheckersGame(local, local, 4456);
			}
		};

		Thread player1 = new Thread() {
			public void run() {
				Player p1 = new Player(Color.BLACK, local, 4456, true);
			}
		};

		Thread player2 = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					Player p2 = new Player(Color.RED, local, 4456, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		server.start();
		player1.start();
		player2.start();
	}

}
