package spaceships;

import javax.swing.JFrame;

public class SpaceshipsGui extends JFrame {
	
	public SpaceshipsGui() {
		super("BM Spaceships");
		super.setSize(600, 600);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SpaceshipsGui();
	}

}
