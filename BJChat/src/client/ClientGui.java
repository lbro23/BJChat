package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import server.ServerGui;

public class ClientGui extends JFrame implements ActionListener, KeyListener {
	private final static String newLine = "\n";
	JTextPane console;
	JTextField input;
	JButton button;
	Client cli;
	String recentInput;
	int port = 4445;
	
	public ClientGui(){
		super("BJ Chat");
		this.setLocation(300, 100);
		this.setSize(700, 600);
		this.addWindowListener(createWindowListener());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupGui();
		this.setVisible(true);
		Socket s = connect();
		this.println("Type desired username, then press ENTER");
		String name = userInputNextLine();
		Client cli = new Client(this, s, name);
	}

	private Socket connect() {
		InetAddress serverAddress = null;
		// get address
		recentInput = "";
		Socket sock = null;
		while(sock == null) {
			try {
				this.println("Type desired Server Address, then press ENTER");
				serverAddress = InetAddress.getByName(userInputNextLine());
				sock = new Socket(serverAddress, port);
				
			} catch(UnknownHostException e) {
				this.println("Invalid Host Name! Please Try Again");
			} catch(ConnectException e) {
				this.println("No Server Found! Is the address correct?");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sock;
	}

	private String userInputNextLine() {
		String result;
		while(recentInput.equals("")){
			try{
			Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		result = recentInput;
		recentInput = "";
		return result;
	}

	public void setupGui() {
		input = new JTextField();
		console = new JTextPane();
		button = new JButton("Send");
		
		button.addActionListener(this);
		
		console.setEditable(false);
		console.setPreferredSize(new Dimension(100, this.getHeight() - 100));
		console.setBackground(Color.WHITE);
		
		Box vertical = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		Box whole = Box.createHorizontalBox();
		Box stream = Box.createVerticalBox();
		
		JScrollPane consolePane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		stream.add(new JLabel("Server Console"));
		stream.add(Box.createVerticalStrut(10));
		stream.add(consolePane);
		
		vertical.add(Box.createVerticalStrut(20));
		vertical.add(consolePane);
		vertical.add(Box.createVerticalStrut(10));
		vertical.add(bottom);
		vertical.add(Box.createVerticalStrut(10));
		
		bottom.add(input);
		bottom.add(Box.createHorizontalStrut(10));
		bottom.add(button);
		
		
		whole.add(Box.createHorizontalStrut(10));
		whole.add(vertical);
		whole.add(Box.createHorizontalStrut(10));
		
		input.addKeyListener(this);
		this.add(whole);
	}
	
	public void println(String message) {
		console.setText(console.getText() + message + "\n");
	}

	public void setName(String name){
		this.setTitle(name + "'s BJChat");
	}
	
	public WindowListener createWindowListener() {
		return new WindowListener() {
			public void windowClosing(WindowEvent e) {
				// TODO Add Code Here
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
	public void actionPerformed(ActionEvent paramActionEvent) {
		if(paramActionEvent.getSource() == button) {
			// TODO Send text to client
			if(cli!= null)
				cli.sendLine(input.getText());
			recentInput = input.getText();
			input.setText("");
		}
	}
	
	public static void main(String[] args0){
		ServerGui s = new ServerGui();
		ClientGui p = new ClientGui();
	}

	@Override
	public void keyTyped(KeyEvent paramKeyEvent) {}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		if(paramKeyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			actionPerformed(new ActionEvent(button, 1, "Button Pressed"));
		}
	}

	@Override
	public void keyReleased(KeyEvent paramKeyEvent) {}
	
	

}
