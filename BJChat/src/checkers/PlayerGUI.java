package checkers;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerGUI extends JFrame {
	
	JPanel buttonPane;
	JButton[][] buttons;
	
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
	
	public PlayerGUI() {
		super("BJ Chat Checkers");
		setBounds(100, 50, 600, 680);
		
		buttonPane = new JPanel();
		buttons = new JButton[8][8];
		
		setupImages();
		createButtons();
		setupGui();
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setupImages() {
		try {
			black = new ImageIcon(this.getClass().getResource("Black.png"));
			blackQueen = new ImageIcon(this.getClass().getResource("BlackQueen.png"));
			blackQueenSelected = new ImageIcon(this.getClass().getResource("BlackQueenSelected.png"));
			blackSelected = new ImageIcon(this.getClass().getResource("BlackSelected.png"));
			emptyBlack = new ImageIcon(this.getClass().getResource("EmptyBlack.png"));
			emptyBlackSelected = new ImageIcon(this.getClass().getResource("EmptyBlackSelected.png"));
			emptyWhite = new ImageIcon(this.getClass().getResource("EmptyWhite.png"));
			red = new ImageIcon(this.getClass().getResource("Red.png"));
			redSelected = new ImageIcon(this.getClass().getResource("RedSelected.png"));
			redQueen = new ImageIcon(this.getClass().getResource("RedQueen.png"));
			redQueenSelected = new ImageIcon(this.getClass().getResource("RedQueenSelected.png"));
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public void setupGui() {
		buttonPane.setLayout(new GridLayout(8, 8, 0, 0));
		Box whole = Box.createVerticalBox();
		Box topLine = Box.createHorizontalBox();
		
		topLine.add(new JLabel("Hello World"));
		
		whole.add(topLine);
		whole.add(Box.createVerticalStrut(10));
		whole.add(buttonPane);
		
		this.add(whole);
	}
	
	public void createButtons() {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				buttons[r][c] = new JButton("");
				buttonPane.add(buttons[r][c]);
				if(r % 2 == 0) {
					if(c%2 == 0) {
						buttons[r][c].setIcon(emptyWhite);
						buttons[r][c].setBackground(Color.BLACK);
					}else {
						buttons[r][c].setIcon(emptyWhite);
						buttons[r][c].setBackground(Color.WHITE);
					}
				} else {
					if(c%2 == 1) {
						buttons[r][c].setIcon(emptyBlack);
						buttons[r][c].setBackground(Color.BLACK);
					}else {
						buttons[r][c].setIcon(emptyWhite);
						buttons[r][c].setBackground(Color.WHITE);
					}
				}
			}
		}
	}
	
	public static void main(String[] args ){
		new PlayerGUI();
	}

}
