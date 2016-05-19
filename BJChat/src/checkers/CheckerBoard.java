package checkers;

import java.awt.Color;
import java.io.Serializable;

public class CheckerBoard implements Serializable {
	/**
	 * 
	 */
	/**
	 * 
	 */
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

	public Checker getPiece(int i, int j) {
		return gameBoard[i][j];
	}

}
