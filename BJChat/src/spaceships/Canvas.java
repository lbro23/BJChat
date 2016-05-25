package spaceships;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	@Override
	public void paint(Graphics g){
		Bullet b = new Bullet(100, 100);
		b.draw(g);
	}
}
