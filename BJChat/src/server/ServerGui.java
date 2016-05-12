package server;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ServerGui extends JFrame {
	JTextField input;
	JTextPane console;
	JButton button;
	
	public ServerGui() {
		super("BJ Chat Server");
		this.setLocation(300, 100);
		this.setSize(400, 600);
		this.setVisible(true);
		this.addWindowListener(createWindowListener());
		setupGui();
	}
	
	public void setupGui() {
		input = new JTextField();
		console = new JTextPane();
		button = new JButton("Send");
		
		Box vertical = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.add(input);
		
		vertical.add(scrollPane);
		vertical.add(Box.createVerticalStrut(10));
		vertical.add(bottom);
		
		console.setText("HELLO WORLD");
		console.setSize(200, 200);
		
		bottom.add(input);
		bottom.add(Box.createHorizontalStrut(10));
		bottom.add(button);
		
		this.add(vertical);
	}
	
	public static void main(String[] args) {
		ServerGui gui = new ServerGui();
	}
	
	public WindowListener createWindowListener() {
		return new WindowListener() {
			public void windowClosing(WindowEvent e) {
				// TODO Add Code Here
				System.out.println("Window Closing");
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		};
		
	}

}
