package checkers;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlayerGUI extends JFrame implements ActionListener, WindowListener, KeyListener{
	
	Player player;
	JPanel buttonPane;
	JButton[][] buttons;
	JLabel label;
	ImageManager manager;
	CheckerBoard lastBoard;
	int[] selected; // two selected buttons (r1, c1, r2, c2)
	boolean enabled = false;
	
	public PlayerGUI(Player player, String name, boolean reversed) {
		super(name);
		setBounds(100, 50, 680, 720);
		manager = new ImageManager();
		this.player = player;
		
		selected = new int[4];
		for(int i = 0; i < 4; i++) {selected[i] = -1; }
		
		buttonPane = new JPanel();
		buttons = new JButton[8][8];
		
		super.addWindowListener(this);
		createButtons(reversed);
		setupGui();
		
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void setupGui() {
		label = new JLabel("Initializing...");
		buttonPane.setBackground(Color.YELLOW);
		buttonPane.setLayout(new GridLayout(8, 8, 5, 5));
		Box whole = Box.createVerticalBox();
		Box topLine = Box.createHorizontalBox();
		
		JButton submit = new JButton("Submit Move");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(player.getNewMove())
				player.setSubmit(true);
			}
		});
		
		topLine.add(label);
		topLine.add(Box.createHorizontalStrut(10));
		topLine.add(submit);
		
		whole.add(topLine);
		whole.add(Box.createVerticalStrut(10));
		whole.add(buttonPane);
		
		this.add(whole);
	}
	
	public void createButtons(boolean reversed) {
		if (reversed) {
			for (int r = 7; r >= 0; r--) {
				for (int c = 0; c < 8; c++) {
					buttons[r][c] = new JButton("");
					buttonPane.add(buttons[r][c]);
					if (r % 2 == c % 2) {
						buttons[r][c].addKeyListener(this);
						buttons[r][c].addActionListener(this);
						buttons[r][c].setIcon(manager.getEmptyBlack());
						buttons[r][c].setBackground(Color.BLACK);
					} else {
						buttons[r][c].setIcon(manager.getEmptyWhite());
						buttons[r][c].setBackground(Color.WHITE);
					}
				}
			}
		} else {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					buttons[r][c] = new JButton("");
					buttonPane.add(buttons[r][c]);
					if (r % 2 == c % 2) {
						buttons[r][c].addKeyListener(this);
						buttons[r][c].addActionListener(this);
						buttons[r][c].setIcon(manager.getEmptyBlack());
						buttons[r][c].setBackground(Color.BLACK);
					} else {
						buttons[r][c].setIcon(manager.getEmptyWhite());
						buttons[r][c].setBackground(Color.WHITE);
					}
				}
			}
		}
	}
	
	public void updateBoard(CheckerBoard board) {
		if(board == null) {player.close(); return; }
		lastBoard = board;
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(r%2 == c%2) { // if black square
					boolean isSelected = (selected[0] == r && selected[1] == c) || (selected[2] == r && selected[3] == c);
					Checker check = board.getPiece(r, c);
					buttons[r][c].setIcon(manager.getCheckerImage(check, isSelected));
				}
			}
		}
	}
	
	private void updateBoard() {
		updateBoard(lastBoard);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(enabled) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (buttons[r][c] == arg0.getSource()) {
						if (selected[0] == r && selected[1] == c) { // already selected, erase selections
							for (int i = 0; i < 4; i++) {
								selected[i] = -1;
							}
							updateBoard();
						} else if (selected[2] == r && selected[3] == c) { // already selected second
							selected[2] = -1;
							selected[3] = -1;
							updateBoard();
						} else { // new selection
							if (selected[0] == -1) { // first selection
								selected[0] = r;
								selected[1] = c;
								updateBoard();
							} else { // second selection
								int row = r;
								int col = c;
								updateBoard();
								Checker check = lastBoard.getPiece(selected[0], selected[1]);
								if(!player.getNewMove()){
								if (check == null) {
									invalidMove();
									return;
								} else if (player.isValidCaptureMove(check, row, col)) {
									for (int i = 0; i < 4; i++) { selected[i] = -1; }
									player.makeCaptureMove(check, row, col);
								} else if(player.isValidPlebMove(check, row, col)) {
									for (int i = 0; i < 4; i++) { selected[i] = -1; }
									player.makeMove(check, row, col);
								} else {
									invalidMove();
									return;
								}
							} else{
								//second move
								if(check == null){
									invalidMove();
									return;
									}else if(player.isValidSecondMove(check, row, col)){
										player.makeCaptureMove(check, row, col);
									} else{
										invalidMove();
										return;
									}
								}
							}
						}
						break;
					}
				}
			}
		}
		
	}
	
	public void victory() {
		JOptionPane.showMessageDialog(null, "Congratulations! You Won!");
		this.dispose();
	}
	
	public void loss() {
		JOptionPane.showMessageDialog(null, "You Lose");
		this.dispose();
	}
	
	public void enable() {
		enabled = true;
		label.setText("Make your move");
	}
	
	public void disable() {
		enabled = false;
		label.setText("Waiting for other player...");
		for (int i = 0; i < 4; i++) { selected[i] = -1; }
	}
	
	private void invalidMove() {
		for(int i = 0; i < 4; i++) { selected[i] = -1; }
		updateBoard();
		JOptionPane.showMessageDialog(null, "Invalid Move!\nTo see rules, click the help button.");
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		player.close();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(player.getNewMove())
				player.setSubmit(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
