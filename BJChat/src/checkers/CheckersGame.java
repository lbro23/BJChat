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
import java.util.HashSet;
import java.util.Set;

public class CheckersGame {
	boolean closed = false;
	int playersTurn;
	CheckerBoard board;
	
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
	
	Set<Thread> threads;
	boolean running;

	
	public CheckersGame(Socket player1, Socket player2){
		try{
			threads = new HashSet<Thread>();
			p1 = player1;
			p2 = player2;

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
					while (running && !interrupted()) {
						try {
							board1 = (CheckerBoard) p1Input.readObject();
							if (board1 == null) {
								p2Output.writeObject(null);
								close(); break;
							} 
							newBoard1 = true;
						} catch (EOFException | SocketException e) {
							running = false;
							break;
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			Thread t2 = new Thread() {
				public void run() {
					while (running && !interrupted()) {
						try {
							board2 = (CheckerBoard) p2Input.readObject();
							if (board2 == null) {
								p1Output.writeObject(null);
								close(); break;
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
				t1.setName("Server P1 Handler");
				threads.add(t1);
				t2.start();
				t2.setName("Server P2 Handler");
				threads.add(t2);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void run() {
		while(running){
			board = getMove(playersTurn);
			
			if(playersTurn == 1) playersTurn = 2; // change to other
			else playersTurn = 1;
			
			updateOtherPlayer(playersTurn);
			if(board.hasWinner()) { close(); }
		}
	}
	
	private void close() {
		if (!closed) {
			try {
				running = false;
				p1Input.close();
				p1Output.close();
				p2Input.close();
				p2Output.close();

				p1.close();
				p2.close();

				//for(Thread t: threads) t.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closed = true;
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
}
