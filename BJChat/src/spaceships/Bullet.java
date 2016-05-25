package spaceships;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Actor  {
	
	public Bullet(double x, double y) {
		super(x, y);
	}
	

	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillOval((int)x, (int)y, 10, 10);
	}

}
