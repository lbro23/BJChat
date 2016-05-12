package client;

import javax.swing.JFrame;

public class ClientGui extends JFrame {
	
	public ClientGui(){
		super("BJChat");
		setupGui();
	}

	private void setupGui() {
		
		
	}
	
	public void setName(String name){
		this.setTitle(name + "'s BJChat");
	}

}
