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
		toPaint = new ArrayList<Actor>();
	}

	@Override
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, 1000, 1000);
		Bullet b = new Bullet(300, loc++);
		b.draw(g);
		for(Actor a: toPaint) {
			a.draw(g);
		}
	}
	
	public void add(Actor a) {
		toPaint.add(a);
	}
	
	public void remove(Actor a) {
		toPaint.remove(a);
	}
}
