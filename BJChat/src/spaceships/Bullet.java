package spaceships;

import java.awt.Graphics;

public class Bullet extends Actor  {
	
	public Bullet(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g){
		g.drawOval(x, y, 2, 2);
	}

}
