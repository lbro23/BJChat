package spaceships;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	List<Actor> toPaint;
	int loc = 0;
	
	public Canvas(){
		super();
	}

	@Override
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, 1000, 1000);
		if(toPaint == null) return; 
		for(Actor a: toPaint) {
			a.draw(g);
		}
	}
	
	public void paintActors(List<Actor> actors) {
		this.toPaint = actors;
	}
}
