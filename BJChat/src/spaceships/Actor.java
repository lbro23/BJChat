package spaceships;

import java.awt.Graphics;

public abstract class Actor {
	protected double x;
	protected double y;
	protected double dX;
	protected double dY;
	
	public abstract void draw(Graphics gs);
	
	public Actor(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void moveTo(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		x += dX;
		y += dY;
	}
	
	public void setDirection(double dX, double dY) {
		this.dX = dX;
		this.dY = dY;
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

}
