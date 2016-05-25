package spaceships;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	public Canvas(){
		super();
	}

	@Override
	public void paint(Graphics g){
		Bullet b = new Bullet(250, 250);
		b.draw(g);
	}
}
