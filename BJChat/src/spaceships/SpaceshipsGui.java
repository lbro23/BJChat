package spaceships;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceshipsGui extends JFrame implements KeyListener {
	private final int thrust = KeyEvent.VK_W;
	private final int brake = KeyEvent.VK_S;
	private final int left = KeyEvent.VK_A;
	private final int right = KeyEvent.VK_D;
	private final int fire = KeyEvent.VK_SPACE;
	
	Canvas canvas;
	
	public SpaceshipsGui() {
		super("BM Spaceships");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setupGui();
		setVisible(true);
	}
	
	public void setupGui() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(500, 500));
		canvas.setBackground(Color.BLACK);
		
		Box wholeH = Box.createHorizontalBox();
		Box wholeV = Box.createVerticalBox();
		
		wholeV.add(Box.createVerticalStrut(10));
		wholeV.add(canvas);
		wholeV.add(Box.createVerticalStrut(10));
		
		wholeH.add(Box.createHorizontalStrut(10));
		wholeH.add(wholeV);
		wholeH.add(Box.createHorizontalStrut(10));
		
		this.add(wholeH);
	}
	
	public void drawGame(GameState state) {
		canvas.paintActors(state.getActors());
		canvas.repaint();
	}
	

	
	public static void main(String[] args) {
		ArrayList<Actor> actors = new ArrayList<Actor>();
		
		SpaceshipsGui gui = new SpaceshipsGui();
		
		Bullet b = new Bullet(10, 10);
		b.setDirection(1, 1);
		actors.add(b);
		
		Ship shoop = new Ship(100, 100);
		shoop.setDirection(1, 1);
		actors.add(shoop);
		
		GameState state = new GameState(actors);
		
		for(int i = 0; i < 100; i++) {
			gui.drawGame(state);
			state.updateAll();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == thrust) {
			// TODO thrust pressed
		} else if(e.getKeyCode() == left){
			// TODO left pressed
		} else if(e.getKeyCode() == right){
			// TODO right pressed
		} else if(e.getKeyCode() == brake){
			// TODO brake pressed
		} else if(e.getKeyCode() == fire){
			// TODO fire pressed
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}

