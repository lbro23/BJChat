package checkers;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Player {
	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	Color team;
	CheckerBoard board;
	PlayerGUI gui;
	boolean newMove;
	
	
	public Player(Color team, InetAddress serverAddress, int port, boolean start){
		try {
			socket = new Socket(serverAddress, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			board = (CheckerBoard)input.readObject();
			
			this.team = team;
			newMove = false;
			
			gui = new PlayerGUI(this, "BJ Chat Checkers " + start);
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
			while (true) {
				otherTurn();
				yourTurn();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void yourTurn() throws InterruptedException, IOException {
		// TODO Your turn
		gui.enable();
		while (!newMove) {
			Thread.sleep(3);
		}
		gui.disable();
		newMove = false;
		output.writeObject(board);
	}
	
	public void otherTurn() throws ClassNotFoundException, IOException {
		// TODO Other player's turn
		board = (CheckerBoard)input.readObject();
		System.out.println("Board Received");
		gui.updateBoard(board);
	}
	
	public boolean isValidPlebMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if (board.getPiece(row, col) == null) {
			int curCol = c.getCol();
			int curRow = c.getRow();
			boolean king = c.isKing();
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
	
	public boolean isValidCaptureMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if (board.getPiece(row, col) == null) {
			int curCol = c.getCol();
			int curRow = c.getRow();
			boolean king = c.isKing();
			if (team.equals(Color.black)) { // black is 1, red is 2
				if (king) {
					if ((row == curRow - 2
							&& ((col == curCol + 2 && board.getPiece(curRow - 1, curCol + 1).getTeam() == 2
									|| (col == curCol - 2 && board.getPiece(curRow - 1, curCol - 1).getTeam() == 2)))))
						return true;
				}
				return ((row == curRow + 2
						&& ((col == curCol + 2 && board.getPiece(curRow + 1, curCol + 1).getTeam() == 2
								|| (col == curCol - 2 && board.getPiece(curRow - 1, curCol - 1).getTeam() == 2)))));
			} else {
				if (king) {
					if ((row == curRow + 2
							&& ((col == curCol + 2 && board.getPiece(curRow + 1, curCol + 1).getTeam() == 1
									|| (col == curCol - 2 && board.getPiece(curRow - 1, curCol - 1).getTeam() == 1)))))

						return true;
				}
				return ((row == curRow - 2
						&& ((col == curCol + 2 && board.getPiece(curRow - 1, curCol + 1).getTeam() == 1
								|| (col == curCol - 2 && board.getPiece(curRow - 1, curCol - 1).getTeam() == 1)))));
			}
		}
		return false;
	}
	
	public void makeMove(Checker src, int newRow, int newCol) {
		board = new CheckerBoard(board, src, newRow, newCol);
		gui.updateBoard(board);
		newMove = true;
	}
	
	public void updateBoard(CheckerBoard newBoard){
		this.board = newBoard;
	}

}
