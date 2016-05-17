package checkers;

import java.awt.Color;

public class CheckerBoard {
	Checker[][] gameBoard;
	
	public CheckerBoard(){
		gameBoard = new Checker[8][8];
		for(int r = 0; r < 3; r++){//assign black pieces
			for(int c = 0; c< gameBoard[r].length;c++){
				if(r%2 == 0 && c%2 == 0){
					gameBoard[r][c] = new Checker(r, c, Color.black);
				}else if(r%2 != 0 && c%2 != 0){
					gameBoard[r][c] = new Checker(r, c, Color.black);
				}
			}
		}
		
		for(int r = 5; r < gameBoard.length; r++){//assign red pieces
			for(int c = 0; c< gameBoard[r].length;c++){
				if(r%2 == 0 && c%2 == 0){
					gameBoard[r][c] = new Checker(r, c, Color.red);
				}else if(r%2 != 0 && c%2 != 0){
					gameBoard[r][c] = new Checker(r, c, Color.red);
				}
			}
		}
		
		
	}//end constructor

}
