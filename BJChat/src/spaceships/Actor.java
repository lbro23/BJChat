package spaceships;

import java.awt.Graphics;

public abstract class Actor {
	protected int x;
	protected int y;
	
	public abstract void draw(Graphics gs);
	
	public Actor(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}
