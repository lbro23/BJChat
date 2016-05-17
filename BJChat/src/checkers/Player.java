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

}
