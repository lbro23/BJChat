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
	int currentHeading = 180;
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

		Polygon Triangle;
		int centerX = (xVals[0] + xVals[1])/2;
		int centerY = (yVals[0] + yVals[2])/2;
		int size = (int) Math.sqrt((centerX-xVals[0]) * (centerX-xVals[0])  + (centerY-yVals[0])*(centerY-yVals[0]));
		rotate(xVals, yVals, size);
		Triangle = new Polygon(xVals, yVals, 3);
		
	
		
		g.fillPolygon(Triangle);
		
		g.setColor(Color.red);
		
		yVals[0] += height;
		xVals[1] = (int) (x - width/2);
		yVals[1] = yVals[0];
		xVals[2] = (int) x;
		yVals[2] = yVals[0] - width/2;
		
		 centerX = (xVals[0] + xVals[1])/2;
		 centerY = (yVals[0] + yVals[2])/2;
		 size = (int) Math.sqrt((centerX-xVals[0]) * (centerX-xVals[0])  + (centerY-yVals[0])*(centerY-yVals[0]));
			rotate(xVals, yVals, size);
		Triangle = new Polygon(xVals, yVals, 3);
		g.fillPolygon(Triangle);
		
		xVals[0] = (int) (x + width);
		xVals[1] = (int) (xVals[0] + width/2);
		xVals[2] = xVals[0];
		
		 centerX = (xVals[0] + xVals[1])/2;
		 centerY = (yVals[0] + yVals[2])/2;
		 size = (int) Math.sqrt((centerX-xVals[0]) * (centerX-xVals[0])  + (centerY-yVals[0])*(centerY-yVals[0]));
			rotate(xVals, yVals, size);
		Triangle = new Polygon(xVals, yVals, 3);
		g.fillPolygon(Triangle);
		
		
	}
	private void rotate(int[] x, int[] y, int size) {
	
		
			double a = 360/x.length;
		    for(int i = 0; i<x.length && i<y.length; i++){
		    double angle = Math.toRadians(currentHeading + i*a);
	    	double cosAngle = Math.cos(angle);
		    double sinAngle = Math.sin(angle);
	    
	    	x[i] = (int) (x[i] + size*cosAngle);
	    	y[i] = (int) (y[i] + size *sinAngle);
	    }
	    
	}
	
	
}
