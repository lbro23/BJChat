package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class DirectMessageWindow extends JFrame implements ActionListener, KeyListener, WindowListener{
	Client client;
	JTextPane text;
	JTextField input;
	JButton button;
	JScrollBar scrollBar;
	
	public DirectMessageWindow(String name, Client c) {
		super("BJ Chat DM with " + name);
		this.setLocation(500, 100);
		this.setSize(300, 400);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.client = c;
		setupGui();
		this.setVisible(true);
	}

	private void setupGui() {
		button = new JButton("Send");
		
		text = new JTextPane();
		text.setEditable(false);
		text.setPreferredSize(new Dimension(100, 300));
		
		input = new JTextField();
		text.setEditable(true);
		
		Box whole = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		
		JScrollPane consolePane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = consolePane.getVerticalScrollBar();
		
		bottom.add(input);
		bottom.add(Box.createHorizontalStrut(10));
		bottom.add(button);
		
		whole.add(Box.createVerticalStrut(10));
		whole.add(consolePane);
		whole.add(Box.createVerticalStrut(10));
		whole.add(bottom);
		whole.add(Box.createVerticalStrut(10));
		
		this.add(whole);
	}
	
	public void println(String message) {
		text.setText(text.getText() + message + "\n");
		scrollDown();
	}
	
	private void scrollDown() {
		if(scrollBar != null) {
			scrollBar.setValue(scrollBar.getMaximum());
		}
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
	
	public static void main(String[] args) { new DirectMessageWindow("Leon", null); }

}
