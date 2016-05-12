package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import client.Client;

public class ServerGui extends JFrame implements ActionListener, KeyListener{
	Server server;
	JTextField input;
	JTextPane console;
	JTextPane users;
	JButton button;
	PrintStream output;
	Scanner serverInput;
	
	public ServerGui() {
		super("BJ Chat Server");
		this.setLocation(300, 100);
		this.setSize(700, 600);
		this.addWindowListener(createWindowListener());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupGui();
		this.setVisible(true);
		server = new Server(4445, this);
	}
	
	public void setupGui() {
		input = new JTextField();
		console = new JTextPane();
		users = new JTextPane();
		button = new JButton("Send");
		
		button.addActionListener(this);
		
		console.setEditable(false);
		console.setPreferredSize(new Dimension(100, this.getHeight() - 100));
		console.setBackground(Color.WHITE);
		
		users.setEditable(false);
		users.setPreferredSize(new Dimension(50, this.getHeight() - 100));
		users.setBackground(Color.GRAY);
		
		Box vertical = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		Box whole = Box.createHorizontalBox();
		Box top = Box.createHorizontalBox();
		Box stream = Box.createVerticalBox();
		Box clients = Box.createVerticalBox();
		
		JScrollPane consolePane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane userPane = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		stream.add(new JLabel("Server Console"));
		stream.add(Box.createVerticalStrut(10));
		stream.add(consolePane);
		
		clients.add(new JLabel("Connected Users"));
		clients.add(Box.createVerticalStrut(10));
		clients.add(userPane);
		
		top.add(stream);
		top.add(Box.createHorizontalStrut(10));
		top.add(clients);
		
		vertical.add(Box.createVerticalStrut(20));
		vertical.add(top);
		vertical.add(Box.createVerticalStrut(10));
		vertical.add(bottom);
		vertical.add(Box.createVerticalStrut(10));
		
		bottom.add(input);
		bottom.add(Box.createHorizontalStrut(10));
		bottom.add(button);
		
		
		whole.add(Box.createHorizontalStrut(10));
		whole.add(vertical);
		whole.add(Box.createHorizontalStrut(10));
		
		this.add(whole);
	}
	
	public static void main(String[] args) {
		ServerGui gui = new ServerGui();
		
		Thread t = new Thread() { 
			public void run() {
				Client s = new Client();
			}
		};
		t.start();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			this.println(input.getText());
			input.setText("");
		}
	}
	
	public void println(String message) {
		String currentText = console.getText();
		currentText += message + "\n";
		console.setText(currentText);
		//console.update(this.getGraphics());
	}

	@Override
	public void keyTyped(KeyEvent paramKeyEvent) {}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		actionPerformed(new ActionEvent(button, 0, "Button Pressed"));
	}

	@Override
	public void keyReleased(KeyEvent paramKeyEvent) {}

}
