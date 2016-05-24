package checkers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.ClientHandler;

public class GameServer {
	ServerSocket serverSock;
	ArrayList<CheckersGame> games;
	
	public GameServer(int port) {
		try {
			games = new ArrayList<CheckersGame>();
			serverSock = new ServerSocket(port);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startMatch(InetAddress c1, InetAddress c2) {
		Socket p1 = null;
		Socket p2 = null;
		
		try { 
			while(p1 == null || p2 == null) {
				Socket s = serverSock.accept();
				if(s.getInetAddress().equals(c1) && p1 == null) {
					p1 = s;
				} else if(s.getInetAddress().equals(c2) && p2 == null) {
					p2 = s;
				} else {
					s.close();
					s = null;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		games.add(new CheckersGame(p1,p2));
	}
}
