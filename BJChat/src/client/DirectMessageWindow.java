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
import javax.swing.JOptionPane;
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
	String target, source;
	
	public DirectMessageWindow(String target, String source, Client c) {
		super("BJ Chat DM with " + target);
		this.target = target;
		this.source = source;
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
		input = new JTextField();
		
		text.setPreferredSize(new Dimension(100, 300));
		text.setEditable(false);
		input.setEditable(true);
		button.addActionListener(this);
		input.addKeyListener(this);
		
		Box wholeH = Box.createHorizontalBox();
		Box wholeV = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		
		JScrollPane consolePane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = consolePane.getVerticalScrollBar();
		text.setEditable(false);
		
		bottom.add(input);
		bottom.add(Box.createHorizontalStrut(10));
		bottom.add(button);
		
		wholeV.add(Box.createVerticalStrut(10));
		wholeV.add(consolePane);
		wholeV.add(Box.createVerticalStrut(10));
		wholeV.add(bottom);
		wholeV.add(Box.createVerticalStrut(10));
		
		wholeH.add(Box.createHorizontalStrut(10));
		wholeH.add(wholeV);
		wholeH.add(Box.createHorizontalStrut(10));
		
		this.add(wholeH);
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
	
	public String getName() {
		return target;
	}
	
	public void sendLine(String line) {
		text.setText(text.getText() + line + "\n");
	}
	
	///////////////// LISTENER METHODS //////////////////
	// Key Listener
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER && !input.getText().equals("")) {
			println(source + ": " + input.getText());
			client.sendLine("\\dmmessage " + target + " " + input.getText());
			input.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	// Action Listener
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == button && !input.getText().equals("")) {
			println(source + ": " + input.getText());
			client.sendLine("\\dmmessage " + target + " " + input.getText());
			input.setText("");
		}
	}
	
	/**
	 * Closed is used when another client closes this
	 */
	public void close() {
		JOptionPane.showMessageDialog(null, "Other user has Exited this DM Session.\n Closing");
		client.removeDM(this);
		dispose();
	}
	
	/**
	 * Exit is used when this client is closing the session
	 */
	public void exit() {
		client.sendLine("\\dmclose " + target + " " + source);
		dispose();
	}

	// Window Listener
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) { client.sendLine("\\dmclose " + target + " " + source); client.removeDM(this); }

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}
