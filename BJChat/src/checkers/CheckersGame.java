package checkers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CheckersGame extends Thread {
	int playersTurn;
	CheckerBoard board;
	
	//player 1 fields
	Socket p1;
	ObjectOutputStream p1Output;
	ObjectInputStream p1Input;
	
	//player 2 fields
	Socket p2;
	ObjectOutputStream p2Output;
	ObjectInputStream p2Input;
	
	
	
	@Override
	public void run() {
		while(true){
		board = waitForMove(playersTurn);
		updateOtherPlayer(playersTurn);
		}
	}
	
	private void updateOtherPlayer(int playersTurn2) {
		
		
	}

	public CheckersGame(Socket Player1, Socket Player2){
		try{
		p1 = Player1;
		p1Input = new ObjectInputStream(p1.getInputStream());
		p1Output = new ObjectOutputStream(p1.getOutputStream());
		
		p2 = Player2;
		p2Input = new ObjectInputStream(p2.getInputStream());
		p2Output = new ObjectOutputStream(p2.getOutputStream());
		board = new CheckerBoard();
		this.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	
	private CheckerBoard waitForMove(int player) {
		try{
			CheckerBoard newBoard;
		if(player == 1){
			newBoard = (CheckerBoard) p1Input.readObject();
		}else{
			newBoard = (CheckerBoard) p2Input.readObject();
			}
		return newBoard;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
