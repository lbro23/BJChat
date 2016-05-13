package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import server.ServerGui;
//
public class ClientGui extends JFrame implements ActionListener, KeyListener {
	private final static String newLine = "\n";
	JTextPane console;
	JTextField input;
	JButton button;
	Client cli;
	JTextPane users;
	int port = 4445;
	
	public ClientGui(){
		super("BJ Chat");
		this.setLocation(700, 100);
		this.setSize(700, 600);
		this.addWindowListener(createWindowListener());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupGui();
		this.setVisible(true);
		Socket s = connect();
		if(s == null) return;
		String name = JOptionPane.showInputDialog(null, "Input Desired Username");
		if(name == "" || name == null) { name = s.getLocalAddress().getHostName().substring(0, 11); }
		cli = new Client(this, s, name);
	}

	private Socket connect() {
		InetAddress serverAddress = null;
		// get address
		Socket sock = null;
		while(sock == null) {
			try {
				String s = JOptionPane.showInputDialog(null, "Input Valid Server Address");
				if(s == null || s.equals("")) {
					if(JOptionPane.showConfirmDialog(null, "Empty Input Received. Close?") == 0) {
						closeWindow();
						break;
					}
					
				}
				serverAddress = InetAddress.getByName(s);
				sock = new Socket(serverAddress, port);
				
			} catch(UnknownHostException e) {
				this.println("Invalid Host Name! Please Try Again");
			} catch(ConnectException e) {
				this.println("No Server Found! Is the address correct? ");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sock;
	}

	public void setupGui() {
		input = new JTextField();
		console = new JTextPane();
		button = new JButton("Send");
		users = new JTextPane();
		
		button.addActionListener(this);
		
		console.setEditable(false);
		console.setPreferredSize(new Dimension(100, this.getHeight() - 100));
		console.setBackground(Color.WHITE);
		
		users.setEditable(false);
		users.setPreferredSize(new Dimension(50, this.getHeight() - 100));
		users.setBackground(Color.LIGHT_GRAY);
		
		Box vertical = Box.createVerticalBox();
		Box bottom = Box.createHorizontalBox();
		Box whole = Box.createHorizontalBox();
		Box stream = Box.createVerticalBox();
		Box clients = Box.createVerticalBox();
		
		JScrollPane consolePane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		consolePane.getVerticalScrollBar().addAdjustmentListener(createAdjListener());
		
		JScrollPane userPane = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		clients.add(new JLabel("Connected Users"));
		clients.add(Box.createVerticalStrut(10));
		clients.add(userPane);
		
		stream.add(new JLabel("Server Console"));
		stream.add(Box.createVerticalStrut(10));
		stream.add(consolePane);
		stream.add(Box.createHorizontalStrut(10));
		
		
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
		whole.add(clients);
		whole.add(Box.createHorizontalStrut(10));
		
		input.addKeyListener(this);
		this.add(whole);
	}
	
	public void println(String message) {
		console.setText(console.getText() + message + "\n");
	}
	
	public void cleanConsole(String message) { console.setText(message); }
	
	public void closeWindow() { setVisible(false); dispose(); }
	
	public WindowListener createWindowListener() {
		return new WindowListener() {
			public void windowClosing(WindowEvent e) {
				if(!cli.dead) {
					cli.sendLine("\\kill");
				}
				try{ Thread.sleep(1000); }
				catch(Exception p) {p.printStackTrace();}
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
			cli.sendLine(input.getText());
			input.setText("");
		}
	}
	
	public AdjustmentListener createAdjListener() {
		return new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    };
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
	
	public void updateUserPane(String[] userNames){
		users.setText("");
		for(int i =0; i<userNames.length; i++){
			users.setText(users.getText() + userNames[i] + "\n");;
		}
	}

	public static void main(String[] args0){
		ClientGui cli = new ClientGui();
	}
}
