package checkers;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
//this is stupid
public class Player {
	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	Color team;
	int teamNum;
	CheckerBoard board;
	PlayerGUI gui;
	Thread io;
	boolean newMove;
	boolean newBoard;
	boolean running;
	boolean closed = false;
	
	
	public Player(Color team, InetAddress serverAddress, int port, boolean start){
		try {
			socket = new Socket(serverAddress, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			board = (CheckerBoard)input.readObject();
			
			running = true;
			this.team = team;
			if(team.equals(Color.BLACK)) teamNum = 1;
			else teamNum = 2;
			newMove = false;
			readInput();
			
			gui = new PlayerGUI(this, "BJ Chat Checkers " + start, start);
			gui.updateBoard(board);
			
			if(start) {
				gui.enable();
				yourTurn();
			} else gui.disable();
			
			playGame();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playGame() {
		try {
			while (running) {
				otherTurn();
				yourTurn();
			}
		} catch (SocketException e) {
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void yourTurn() throws InterruptedException, IOException, SocketException {
		// TODO Your turn
		gui.enable();
		while (!newMove && running) {
			Thread.sleep(3);
		}
		gui.disable();
		newMove = false;
		try {
			output.writeObject(board);
		} catch(SocketException e) { running = false; }
	}

	public void otherTurn() throws ClassNotFoundException, IOException, InterruptedException {
		while (!newBoard && running) {
			Thread.sleep(3);
		}
		newBoard = false;
		gui.updateBoard(board);
	}
	
	public boolean isValidPlebMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if (board.getPiece(row, col) == null) {
			int curCol = c.getCol();
			int curRow = c.getRow();
			boolean king = c.isKing();
			if(c.getTeam() != teamNum) return false;
			if (team.equals(Color.black)) {
				if (king) {
					if (row >= 0 && row < 9 && col >= 0 && col < 9
							&& (row == curRow - 1 && (col == curCol + 1 || col == curCol - 1)))
						return true;
				}
				return (row >= 0 && row < 9 && col >= 0 && col < 9
						&& (row == curRow + 1 && (col == curCol + 1 || col == curCol - 1)));

			} else {
				if (king) {
					if (row >= 0 && row < 9 && col >= 0 && col < 9
							&& (row == curRow + 1 && (col == curCol + 1 || col == curCol - 1)))
						return true;
				}
				return (row >= 0 && row < 9 && col >= 0 && col < 9
						&& (row == curRow - 1 && (col == curCol + 1 || col == curCol - 1)));
			}
		}
		return false;
	}
	
	public boolean isValidCaptureMove(Checker c, int row, int col) {// checks to see if this is a valid move
		if (c.getTeam() != teamNum) return false; // non matching teams, false
		if (board.getPiece(row, col) != null) return false; // destination occupied
		
		int curCol = c.getCol();
		int curRow = c.getRow();
		int midRow = (c.getRow() + row) / 2;
		int midCol = (c.getCol() + col) / 2;
		int diffR = c.getRow() - row;
		int diffC = c.getCol() - col;
		boolean king = c.isKing();

		if (Math.abs(diffC) != 2 || Math.abs(diffR) != 2) return false; // either too close or too far

		if (team.equals(Color.black)) {
			if (!king && diffR == 2) return false; // if not king and moving backwards
			return board.getPiece(midRow, midCol).getTeam() != teamNum; // return true if the mid checker is not the same as this checker
		} else { // RED
			if (!king && diffR == -2) return false; // if not king and moving backwards
			return board.getPiece(midRow,  midCol).getTeam() != teamNum;
		}
	}
	
	public void makeMove(Checker src, int newRow, int newCol) {
		board = new CheckerBoard(board, src, newRow, newCol);
		gui.updateBoard(board);
		newMove = true;
	}
	
	public void makeCaptureMove(Checker src, int newRow, int newCol) {
		board.removePiece((src.getRow()+newRow)/2, (src.getCol()+newCol)/2);
		makeMove(src, newRow, newCol);
	}
	
	public void updateBoard(CheckerBoard newBoard){
		this.board = newBoard;
	}
	
	public void readInput() {
		Thread t = new Thread() {
			public void run() {
				while (running && !isInterrupted()) {
					try {
						board = (CheckerBoard) input.readObject();
						if(board == null) {
							System.out.println("Null Board Received, Exiting");
							exit();
							break;
						}
						newBoard = true;
					} catch (SocketException | EOFException e) {
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.setName("Player Read Input" + team.toString());
		io = t;
		t.start();
	}
	
	public void close() {
		running = false;
		if(closed) return;
		else closed = true;
		try {
			output.writeObject(null);
			input.close();
			output.close();
			socket.close();
			io.interrupt();
		} catch(Exception e) {  }
		System.out.println("Closing Player");
	}
	
	public void exit() {
		gui.dispose();
		close();
		JOptionPane.showMessageDialog(null, "Other User Has RAGE QUIT, Closing!");
	}

}
