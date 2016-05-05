package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerThread extends Thread {
	DatagramSocket socket;
	boolean running;
	
	public ServerThread() {
		try {
			socket = new DatagramSocket(4445);
		} catch (SocketException e) {
			System.err.println("Socket Exception! Unable to open socket on port 4445");
		}
		running = true;
	}
	
	@Override
	public void run() {
		DatagramPacket packet = null;
		while(running) {
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(packet != null) {
				System.out.println("Message Received!");
				try {
					socket.send(new DatagramPacket(null, 0));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
