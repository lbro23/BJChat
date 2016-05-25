package spaceships;

import javax.swing.JFrame;

public class SpaceshipsGui extends JFrame {
	
	public SpaceshipsGui() {
		super("BM Spaceships");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}
	
	public void setupGui() {
		
	}
	
	public static void main(String[] args) {
		new SpaceshipsGui();
	}

}
