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
	
	public boolean isPlayerPiece(Checker c){
		for(Checker temp: playersPieces)
			if(temp.equals(c))
				return true;
		return false;
		
	}
	
	public boolean isValidPlebMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if(board.getPiece(row, col).equals(null)){
		int curCol = c.getCol();
		int curRow = c.getRow();
		boolean king = c.isKing();
		if(team.equals(Color.black)){
			if(king){
			if(row>=0 && row < 9 && col >= 0 && col <9 && (row == curRow-1 && (col == curCol+1 || col == curCol-1)))
				return true;
			}
			return(row>=0 && row < 9 && col >= 0 && col <9 && (row == curRow+1 && (col == curCol+1 || col == curCol-1)));
			
		}else{
			if(king){
			if(row>=0 && row < 9 && col >= 0 && col <9 && (row == curRow+1 && (col == curCol+1 || col == curCol-1)))
				return true;
			}
			return(row>=0 && row < 9 && col >= 0 && col <9 && (row == curRow-1 && (col == curCol+1 || col == curCol-1)));
		}
		}
		return false;
	}
	
	public boolean isValidCaptureMove(Checker c, int row, int col){//checks to see if the checker can move to the next space
		if(board.getPiece(row, col).equals(null)){
		int curCol = c.getCol();
		int curRow = c.getRow();
		boolean king = c.isKing();
		if(team.equals(Color.black)){
			if(king){
				if((row == curRow-2 && ((col == curCol+2 && board.getPiece(curRow-1, curCol+1).getTeam().equals(Color.red)) || (col == curCol-2 && board.getPiece(curRow-1, curCol-1).getTeam().equals(Color.red)))))
					return true;
			}
			return ((row == curRow+2 && ((col == curCol+2 && board.getPiece(curRow+1, curCol+1).getTeam().equals(Color.red)) || (col == curCol-2 && board.getPiece(curRow-1, curCol-1).getTeam().equals(Color.red)))));
		}else{
			if(king){
				if ((row == curRow+2 && ((col == curCol+2 && board.getPiece(curRow+1, curCol+1).getTeam().equals(Color.black)) || (col == curCol-2 && board.getPiece(curRow-1, curCol-1).getTeam().equals(Color.black)))))

					return true;
			}
				return ((row == curRow-2 && ((col == curCol+2 && board.getPiece(curRow-1, curCol+1).getTeam().equals(Color.black)) || (col == curCol-2 && board.getPiece(curRow-1, curCol-1).getTeam().equals(Color.black)))));
			
			}
		}
		return false;
	}
	
			
	
	
	
	public void upDateBoard(CheckerBoard newBoard){
		this.board = newBoard;
	}

}
