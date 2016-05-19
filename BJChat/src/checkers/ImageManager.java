package checkers;

import javax.swing.ImageIcon;

public class ImageManager {
	ImageIcon black;
	ImageIcon blackQueen;
	ImageIcon blackQueenSelected;
	ImageIcon blackSelected; 
	ImageIcon emptyBlack;
	ImageIcon emptyBlackSelected;
	ImageIcon emptyWhite;
	ImageIcon red;
	ImageIcon redQueen;
	ImageIcon redQueenSelected;
	ImageIcon redSelected;

	public ImageManager() { 
		black = new ImageIcon(getClass().getResource("Black.png"));
		blackQueen = new ImageIcon(getClass().getResource("BlackQueen.png"));
		blackQueenSelected = new ImageIcon(getClass().getResource("BlackQueenSelected.png"));
		blackSelected = new ImageIcon(getClass().getResource("BlackSelected.png"));
		emptyBlack = new ImageIcon(getClass().getResource("EmptyBlack.png"));
		emptyBlackSelected  = new ImageIcon(getClass().getResource("EmptyBlackSelected.png"));
		emptyWhite = new ImageIcon(getClass().getResource("EmptyWhite.png"));
		red = new ImageIcon(getClass().getResource("Red.png"));
		redQueen = new ImageIcon(getClass().getResource("RedSelected.png"));
		redQueenSelected = new ImageIcon(getClass().getResource("RedQueen.png"));
		redSelected  = new ImageIcon(getClass().getResource("RedQueenSelected.png"));
	}
		
	public ImageIcon getEmptyBlack() { return emptyBlack; }
	public ImageIcon getEmptyWhite() { return emptyWhite; }
}
