package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class DirectMessageWindow extends JFrame implements ActionListener, KeyListener, WindowListener{
	Client client;
	
	public DirectMessageWindow(String name, Client c) {
		super("BJ Chat DM with " + name);
		this.setLocation(500, 100);
		this.setSize(300, 200);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.client = c;
	}

	///////////////// LISTENER METHODS //////////////////
	// Key Listener
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	// Action Listener
	@Override
	public void actionPerformed(ActionEvent arg0) {}

	// Window Listener
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

}
