package spaceships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class Ship extends Actor{
	public Ship(double x, double y) {
		super(x, y);
		
	}
	final int height = 20;
	final int width = 20;
	@Override
	public void draw(Graphics g) {
		g.fillRect((int)x, (int)y, width, height);
		int[] xVals = new int[3];
		int[] yVals = new int[3];
		xVals[0] = (int) x;
		yVals[0] = (int) y;
		xVals[1] = (int)x + width;
		yVals[1] = yVals[0];
		xVals[2] = (int) (x+width/2);
		yVals[2]= yVals[0] - width;
		g.setColor(Color.cyan);
		
		g.drawPolygon(new Polygon(xVals, yVals, 3));
		
		g.setColor(Color.red);
		
		yVals[0] += height;
		xVals[1] = (int) (x - width/2);
		yVals[1] = yVals[0];
		xVals[2] = (int) x;
		yVals[2] = yVals[0] - width/2;
		
		g.drawPolygon(new Polygon(xVals, yVals, 3));
		xVals[0] = (int) (x + width);
		xVals[1] = (int) (x + width/2);
		xVals[2] = xVals[0];
		
		g.drawPolygon(new Polygon(xVals, yVals, 3));
		
		
	}
	
	
}
