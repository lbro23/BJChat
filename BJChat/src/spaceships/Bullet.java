package spaceships;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Actor  {
	
	public Bullet(int x, int y) {
		super(x, y);
	}
	

	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillOval(x, y, 10, 10);
	}

}
