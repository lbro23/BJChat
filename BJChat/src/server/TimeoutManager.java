package server;

import java.util.ArrayList;

public class TimeoutManager extends Thread {
	Server master;
	ArrayList<ClientHandler> handlers;
	int checkTime = 5000;

	public TimeoutManager(Server master, ArrayList<ClientHandler> handlers) {
		this.master = master;
		this.handlers = handlers;
	}
	// hi
	@Override // hi
	public void run() { // HI
		
		for(ClientHandler c: handlers) {
			if(System.currentTimeMillis() - c.getLastConnectTime() > checkTime) { // needs to be checked
				
			}
		}
	}
}
