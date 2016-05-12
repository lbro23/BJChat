package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame {
	private final static String newLine = "\n";
	JTextArea chatLog;
	JTextField clientInput;
	JButton send;
	
	public ClientGui(){
		super("BJChat");
		this.setSize(200,200);
		this.setLocation(500, 200);
		setupGui();
		this.setVisible(true);
	}

	private void setupGui() {
		
		Box first = new Box(BoxLayout.Y_AXIS);
		chatLog = new JTextArea();
		chatLog.setEditable(false);
		first.add(chatLog);
		first.setVisible(true);
		
		Box second = new Box(BoxLayout.X_AXIS);
		clientInput = new JTextField();
		clientInput.setEditable(true);
		second.add(clientInput);
		
		send = new JButton("send");
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			getMessage();
			}
			
		});
		second.add(send);
		second.setVisible(true);
		
		this.add(first);
		this.add(second);
		
		
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				sendCloseReport();
			}


			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
	}
	
	protected void sendCloseReport() {
		
		
	}

	public void setName(String name){
		this.setTitle(name + "'s BJChat");
	}
	
	public void addText(String text){
		chatLog.append(text + newLine);
	}
	
	public static void main(String[] args0){
		ClientGui p = new ClientGui();
	}
	public String getMessage(){
		String message = clientInput.getText();
		//clear field here
		return message;
		
	}
	
	

}
