package spaceships;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceshipsGui extends JFrame {
	JPanel canvas;
	
	public SpaceshipsGui() {
		super("BM Spaceships");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}
	
	public void setupGui() {
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(500, 500));
		canvas.setBackground(Color.BLACK);
		Box wholeH = Box.createHorizontalBox();
		Box wholeV = Box.createVerticalBox();
		
		wholeV.add(Box.createVerticalStrut(10));
		wholeV.add(canvas);
		wholeV.add(Box.createVerticalStrut(10));
		
		wholeH.add(Box.createHorizontalStrut(10));
		wholeH.add(wholeV);
		wholeH.add(Box.createHorizontalStrut(10));
		
		this.add(wholeH);
	}
	
	public void drawGame(GameState state) {
		// TODO Draw Game from game state
	}
	
	public static void main(String[] args) {
		new SpaceshipsGui();
	}

}
