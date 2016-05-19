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
	ImageManager manager;
	
	public PlayerGUI() {
		super("BJ Chat Checkers");
		setBounds(100, 50, 600, 680);
		manager = new ImageManager();
		
		buttonPane = new JPanel();
		buttons = new JButton[8][8];
		
		createButtons();
		setupGui();
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
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

				if(r % 2 == c % 2) {
					buttons[r][c].setIcon(manager.getEmptyBlack());
					buttons[r][c].setBackground(Color.BLACK);
				} else {
					buttons[r][c].setIcon(manager.getEmptyWhite());
					buttons[r][c].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	public void updateBoard(CheckerBoard board) {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				Checker check = board.getPiece(r, c);
				if(check == null) { 
					if(r % 2 == c % 2) {
						buttons[r][c].setIcon(manager.getEmptyBlack());
					} else {
						buttons[r][c].setIcon(manager.getEmptyWhite());
					}
				} else {
					
				}
			}
		}
	}
	
	public static void main(String[] args ){
		new PlayerGUI();
	}

}
