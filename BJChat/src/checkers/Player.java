package checkers;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	ArrayList<Checker> playersPieces;
	Color team;
	CheckerBoard board;
	PlayerGUI gui;
	
	
	public Player(Color team, CheckerBoard board){
		this.team = team;
		this.board = board;
		if(team == Color.black){
			assignBlackTeamPieces();
		}else{
			assignRedTeamPieces();
		}
		gui = new PlayerGUI(this);
		gui.updateBoard(board);
	}

	private void assignBlackTeamPieces() {
		
		
	}

	private void assignRedTeamPieces() {
	
		
	}
	
	public boolean isPlayerPiece(Checker c){
		for(Checker temp: playersPieces)
			if(temp.equals(c))
				return true;
		return false;
		
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
	}
	
	public void updateBoard(CheckerBoard newBoard){
		this.board = newBoard;
	}

}
