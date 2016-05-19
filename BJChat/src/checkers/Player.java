package checkers;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	ArrayList<Checker> playersPieces;
	Color team;
	CheckerBoard board;
	
	
	public Player(Color team, CheckerBoard board){
		this.team = team;
		this.board = board;
		if(team == Color.black){
			assignBlackTeamPieces();
		}else{
			assignRedTeamPieces();
		}
	}

	private void assignBlackTeamPieces() {
		
		
	}

	private void assignRedTeamPieces() {
	
		
	}
	
	public boolean isValidMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if(team.equals(Color.black)){
			//do for black moves
		}else{
			//check for valid red move
		}
		return false; // I H8 Errors
		
	}
	
	public void upDateBoard(CheckerBoard newBoard){
		this.board = newBoard;
	}

}
