package checkers;

import java.awt.Color;
import java.io.Serializable;

public class CheckerBoard implements Serializable {

	private static final long serialVersionUID = -8328057335354616348L;
	Checker[][] gameBoard;
	int winner;
	
	public CheckerBoard(){
		winner = -1;
		gameBoard = new Checker[8][8];
		gameBoard[1][1] = new Checker(1, 1, 1);
		gameBoard[7][7] = new Checker(7, 7, 2);
//		for(int r = 0; r < 3; r++){//assign black pieces
//			for(int c = 0; c< gameBoard[r].length;c++){
//				if(r%2 == 0 && c%2 == 0){
//					gameBoard[r][c] = new Checker(r, c, 1); // 1 for black, 2 for red
//				}else if(r%2 != 0 && c%2 != 0){
//					gameBoard[r][c] = new Checker(r, c, 1);
//				}
//			}
//		}
//		
//		for(int r = 5; r < gameBoard.length; r++){//assign red pieces
//			for(int c = 0; c< gameBoard[r].length;c++){
//				if(r%2 == 0 && c%2 == 0){
//					gameBoard[r][c] = new Checker(r, c, 2); // 1 for black, 2 for red
//				}else if(r%2 != 0 && c%2 != 0){
//					gameBoard[r][c] = new Checker(r, c, 2);
//				}
//			}
//		}
		
		
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
		checkWinner();
	}
	
	private void checkKing() {
		for(int i = 0; i < 8; i++) {
			if(gameBoard[0][i] != null && gameBoard[0][i].getTeam() == 2) gameBoard[0][i].makeKing();
			if(gameBoard[7][i] != null && gameBoard[7][i].getTeam() == 1) gameBoard[7][i].makeKing();
		}
	}
	
	private void checkWinner() {
		int redPieces = 0;
		int blackPieces = 0;
		for(Checker[] arr: gameBoard) {
			for(Checker c: arr) {
				if(c != null && c.getTeam()==1) {
					blackPieces++;
				} else if(c!=null && c.getTeam() ==2) {
					redPieces++;
				}
			}
		}
		if(redPieces <= 0) winner = 1;
		if(blackPieces <= 0) winner = 2;
	}
	
	public int getWinner() { return winner; }
	public boolean hasWinner() { return winner == -1; }
	
	public void removePiece(int r, int c) {
		gameBoard[r][c] = null;
	}

	public Checker getPiece(int i, int j) {
		return gameBoard[i][j];
	}

}
