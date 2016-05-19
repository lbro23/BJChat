package checkers;

import java.awt.Color;

public class Checker {
	boolean king = false;
	Color team;
	int row;
	int col;
	
	public Checker(int r, int c, Color team){
		row = r;
		col = c;
		this.team = team;
	}
	
	public void makeKing(){king = true;}
	public boolean isKing(){return king;}
	public int getRow(){return row;}
	public int getCol(){return col;}
	public Color getTeam(){return team;}
	
	public void move(char newRow, int newCol){
		row = newRow;
		col = newCol;
	}

}

