package checkers;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CheckersGame {
	int playersTurn;
	CheckerBoard board;
	ServerSocket serverSocket;
	
	//player 1 fields
	Socket p1;
	ObjectOutputStream p1Output;
	ObjectInputStream p1Input;
	
	//player 2 fields
	Socket p2;
	ObjectOutputStream p2Output;
	ObjectInputStream p2Input;

	
	public CheckersGame(InetAddress player1, InetAddress player2, int port){
		try{
			System.out.println("New Server Created, Awaiting Connection");
			serverSocket = new ServerSocket(port);
			
			// connect
			while(p1 == null || p2 == null) {
				Socket s = serverSocket.accept();
				System.out.println("Client Connected from " + s.getInetAddress().getHostName());
				if(s.getInetAddress().equals(player1) && p1 == null) {
					p1 = s;
					System.out.println("Player 1 connected!");
				} else if(s.getInetAddress().equals(player2) && p2 == null) {
					p2 = s;
					System.out.println("Player 2 connected!");
				} else {
					s = null;
				}
			}
			playersTurn = 1;

			p1Input = new ObjectInputStream(p1.getInputStream());
			p1Output = new ObjectOutputStream(p1.getOutputStream());
			
			p2Input = new ObjectInputStream(p2.getInputStream());
			p2Output = new ObjectOutputStream(p2.getOutputStream());
			
			board = new CheckerBoard();
			p1Output.writeObject(board);
			p2Output.writeObject(board);
			this.run();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while(true){
			board = getMove(playersTurn);
			
			if(playersTurn == 1) playersTurn = 2; // change to other
			else playersTurn = 1;
			
			updateOtherPlayer(playersTurn);
		}
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
			CheckerBoard newBoard;
			if (player == 1) {
				newBoard = (CheckerBoard) p1Input.readObject();
			} else {
				newBoard = (CheckerBoard) p2Input.readObject();
			}
			return newBoard;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
				} catch (Exception e) { e.printStackTrace(); }
			}
		};
		server.start();
		player1.start();
		player2.start();
	}

}
