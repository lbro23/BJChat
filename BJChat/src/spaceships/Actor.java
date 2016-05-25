package spaceships;

import java.awt.Graphics;

public abstract class Actor {
	protected int x;
	protected int y;
	
	public abstract void draw(Graphics gs);
	
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}

}
