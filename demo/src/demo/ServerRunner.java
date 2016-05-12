package demo;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerRunner implements Runnable {

	Socket sock;
	Server master;
	private Scanner input;
	private PrintWriter output;
	private boolean running;

	public ServerRunner(Server master, Socket sock) {
		this.sock = sock;
		this.master = master;
	}

	public void run() {
		try {
			input = new Scanner(sock.getInputStream());
			output = new PrintWriter(sock.getOutputStream());

			running = true;
//			Thread checker = new Thread() {
//				public void run() {
//					while (running) {
//						check();
//					}
//				}
//			};

			Thread reader = new Thread() {
				public void run() {
					while (running) {
						if (input.hasNext()) {
							String message = input.nextLine();

							
							// escape character
							if(message.contains("\\")) {
								// parse for commands
								sayToClient("Command Detected!");
							} else {
								master.say(sock, message);
							}
						}
					}
				}
			};
//			checker.start();
			reader.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void check() {
		if (sock.isInputShutdown()) {
			System.out.println("code ran");
			master.remove(sock);
			running = false;
			try {
				sock.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private void sayToClient(String message) {
		try {
			PrintStream output = new PrintStream(sock.getOutputStream());
			output.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
