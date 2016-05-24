package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
// RUN TEST TO CREATE CLIENTS AND SERVERS
public class TestFrame extends JFrame implements ActionListener{
	JButton client, server;
	
	public TestFrame() {
		super("BM Chat Tester");
		this.setLocation(50, 50);
		this.setSize(250, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		client = new JButton("New Client");
		client.addActionListener(this);
		server = new JButton("New Server");
		server.addActionListener(this);
		
		Box b = Box.createHorizontalBox(); 
		
		b.add(client);
		b.add(Box.createHorizontalStrut(10)); 
		b.add(server);
	
		this.add(b);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TestFrame t = new TestFrame();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == client) {
			Thread t = new Thread() {
				public void run() {
					client.ClientGui c = new client.ClientGui();
				}
			};
			t.start();
		} else if(arg0.getSource() == server) {
			Thread t = new Thread() {
				public void run() {
					server.ServerGui g = new server.ServerGui();			
				}
			};
			t.start();
		}
		
	}

}
