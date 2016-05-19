package checkers;

import java.awt.Color;

public class Checker {
	
	boolean king = false;
	int team; // 0 for black, 2 for red
	int row;
	int col;
	
	public Checker(int r, int c, int team){
		row = r;
		col = c;
		this.team = team;
	}
	
	public void makeKing(){king = true;}
	public boolean isKing(){return king;}
	public int getRow(){return row;}
	public int getCol(){return col;}
	public int getTeam(){return team;}
	
	public void move(int newRow, int newCol){
		row = newRow;
		col = newCol;
	}
	
	public boolean equals(Object other){
		Checker o = (Checker)other;
		return row == o.row && col == o.col && team == o.team;
	}

}

