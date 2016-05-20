package checkers;

import java.awt.Color;
import java.io.Serializable;

public class CheckerBoard implements Serializable {

	private static final long serialVersionUID = -8328057335354616348L;
	Checker[][] gameBoard;
	
	public CheckerBoard(){
		gameBoard = new Checker[8][8];
		for(int r = 0; r < 3; r++){//assign black pieces
			for(int c = 0; c< gameBoard[r].length;c++){
				if(r%2 == 0 && c%2 == 0){
					gameBoard[r][c] = new Checker(r, c, 1); // 1 for black, 2 for red
				}else if(r%2 != 0 && c%2 != 0){
					gameBoard[r][c] = new Checker(r, c, 1);
				}
			}
		}
		
		for(int r = 5; r < gameBoard.length; r++){//assign red pieces
			for(int c = 0; c< gameBoard[r].length;c++){
				if(r%2 == 0 && c%2 == 0){
					gameBoard[r][c] = new Checker(r, c, 2); // 1 for black, 2 for red
				}else if(r%2 != 0 && c%2 != 0){
					gameBoard[r][c] = new Checker(r, c, 2);
				}
			}
		}
		
		
	}//end constructor
	
	/**
	 * Create a new checker board based on another checker board
	 * execute the move below
	 */
	public CheckerBoard(CheckerBoard other, Checker src, int r2, int c2) {
		gameBoard = other.gameBoard;
		gameBoard[src.getRow()][src.getCol()] = null;
		gameBoard[r2][c2] = src;
		src.move(r2, c2);
		checkKing();
	}
	
	private void checkKing() {
		for(int i = 0; i < 8; i++) {
			if(gameBoard[0][i] != null && gameBoard[0][i].getTeam() == 2) gameBoard[0][i].makeKing();
			if(gameBoard[7][i] != null && gameBoard[7][i].getTeam() == 1) gameBoard[7][i].makeKing();
		}
	}
	
	public void removePiece(int r, int c) {
		gameBoard[r][c] = null;
	}

	public Checker getPiece(int i, int j) {
		return gameBoard[i][j];
	}

}
