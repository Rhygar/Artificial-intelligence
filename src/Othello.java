import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A game of Othello.
 * 
 * Player = -1 & COM = 1
 * 
 * @author David Tran & John Tengvall
 *
 */
public class Othello extends JPanel {

	private JPanel pnlCenter = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlEastNorth = new JPanel();
	private JPanel pnlEastSouth = new JPanel();
	private JPanel pnlEastCenter = new JPanel();
	private JButton btnReset = new JButton("START");
	private JPanel[][] box = new JPanel[4][4];
	private JLabel lblOthello = new JLabel("OTHELLO");
	private JLabel lblHumanScore = new JLabel("X");
	private JLabel lblComScore = new JLabel("Y");
	private ImageIcon imagePlayer;
	private ImageIcon imageCom;
	private JLabel jblImagePlayer;
	private JLabel jblImageCom;
	private final int EMPTY = 0, HUMAN = -1, COM = 1;
	private State state;
	private int currentPlayer = -1;

	public Othello() {

		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout());
		lblOthello.setFont(new Font("Serif", Font.PLAIN, 40));
		// Panel center
		pnlCenter.setLayout(new GridLayout(4, 4, 2, 2));
		pnlCenter.setBackground(new Color(0x0066cc));

		// Panel East
		pnlEast.setLayout(new BorderLayout());
		pnlEast.setBackground(Color.GREEN);
		pnlEast.setPreferredSize(new Dimension(200, 400));

		// Panel East North. Contaning OTHELLO label
		pnlEastNorth.setPreferredSize(new Dimension(200, 100));
		// pnlEastNorth.setBackground(Color.GRAY);

		// Panel East South. Containg RESET-button
		pnlEastSouth.setPreferredSize(new Dimension(200, 100));
		// pnlEastSouth.setBackground(Color.GRAY);

		// Panel East Center. Containing Scores
		pnlEastCenter.setLayout(new GridLayout(2, 2));
		imagePlayer = new ImageIcon("src/BlackCircle.png");
		imageCom = new ImageIcon("src/WhiteCircle.png");
		jblImagePlayer = new JLabel(imagePlayer);
		jblImageCom = new JLabel(imageCom);
		pnlEastCenter.add(jblImagePlayer);
		pnlEastCenter.add(lblHumanScore);
		pnlEastCenter.add(jblImageCom);
		pnlEastCenter.add(lblComScore);

		// ResetButton
		btnReset.addActionListener(new AI());
		pnlEastSouth.add(btnReset);

		// adding to Panels
		pnlEast.add(pnlEastNorth, BorderLayout.NORTH);
		pnlEast.add(pnlEastSouth, BorderLayout.SOUTH);
		pnlEast.add(pnlEastCenter, BorderLayout.CENTER);
		pnlEastNorth.add(lblOthello);
		// add 16 JPanel into Gridlayout
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				box[i][j] = new JPanel();
				box[i][j].addMouseListener(new ML(i, j)); // adding
															// mouselistener to
															// each panel
				pnlCenter.add(box[i][j]);
			}
		}
		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
	}

	/**
	 * This method renders the board with updated view
	 */
	public void render() {
		int humanScore = 0, comScore = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == EMPTY) {
					box[i][j].setBackground(new Color(00, 99, 33));
				} else if (state.getOwner(i, j) == HUMAN) {
					box[i][j].setBackground(Color.BLACK);
					humanScore++;
				} else {
					box[i][j].setBackground(Color.WHITE);
					comScore++;
				}
			}
		}
		lblHumanScore.setText(humanScore + "");
		lblComScore.setText(comScore + "");
	
	}

	/**
	 * This method restarts the game.
	 */
	public void reset() {
		btnReset.setText("Reset");
		state = new State();
		render();
	}

	public void changeColor(int row, int col) {
		box[row][col].setBackground(Color.BLACK);
	}

	public void playerMadeMove(int row, int col) {
		// panel.setBackground(Color.WHITE);
		System.out.println("Player put on ROW " + row + " COL " + col);
//		state = checkBoard(state, row, col, 1, 1, -1);
//		render();
		checkAllDirections(state, row, col,currentPlayer);
		render();
		currentPlayer *= -1;
	}
	
	public void comMove() {
		ComMove2 move = new ComMove2();
		int index = move.alphaBetaSearch(state);
		int rowMove = index / 4;
		int colMove = index%4;
		checkAllDirections(state, rowMove, colMove,currentPlayer);
		System.out.println("COM put on row " + rowMove + " Col: " + colMove);
		render();
//		for(int i = 0; i < 4; i++) {
//			for(int j = 0; j < 4; j++) {
//				System.out.print(state.getOwner(i, j));
//			}
//			System.out.println();
//		}
		currentPlayer *= -1;
		
	}
	
	/**
	 * This method calls checkBoard function to check all 8 possible directions
	 * @param state
	 * @param placedRow
	 * @param placedCol
	 * @param player
	 */
	public void checkAllDirections(State state, int placedRow, int placedCol, int player) {
		int[] dirArray = {0,-1,-1,-1,0,1,1,1,0,-1};
		for(int i = 0; i < 8; i++) {
			checkBoard(state, placedRow, placedCol, dirArray[i], dirArray[i+2],player);
		}
	}

	/**
	 * This method places the new piece on the board and checks if any pieces will turn in this specific . 
	 * It then returns a new state with the new board  
	 * @param state The current state 
	 * @param placedRow Row of the placed piece
	 * @param placedCol Column of the placed piece
	 * @param rowDir 
	 * @param colDir
	 * @param player
	 * @return
	 */
	public State checkBoard(State state, int placedRow, int placedCol, int rowDir, int colDir, int player) {

		int checkRow = placedRow + rowDir;
		int checkCol = placedCol + colDir;
		int[][] board = state.getBoard();
		boolean foundOpponentPiece = false;
		int pipsToTurn = 0;
		
		board[placedRow][placedCol] = player;

		while (checkRow >= 0 && checkCol <= 3 && checkCol >= 0 && checkRow <= 3	&& board[checkRow][checkCol] != 0) {
			//if found any of same color and found opponent pieces in between
			if (board[checkRow][checkCol] == player && foundOpponentPiece) {
				for (int i = 1; i <= pipsToTurn; i++) {
					board[checkRow - i * rowDir][checkCol - i * colDir] = player;
				}
				state.setBoard(board);
				return state;
			} else if (!foundOpponentPiece && board[checkRow][checkCol] == player) {
				break;
			} else {
				foundOpponentPiece = true;
				pipsToTurn++;
			}
			checkRow += rowDir;
			checkCol += colDir;
		}
		state.setBoard(board);
		return state;
	}
	
	private class ML implements MouseListener {

		private int i, j;
		private ML(int row, int col) {
			this.i = row;
			this.j = col;
		}

		@Override
		/*
		 * When empty box is clicked, do some AI stuff
		 */
		public void mouseClicked(MouseEvent e) {
			JPanel panel = (JPanel) e.getSource();
			if (panel.getBackground().equals(Color.BLACK)) {
				System.out.println("You clicked on black");
			} else if (panel.getBackground().equals(Color.WHITE)) {
				System.out.println("You clicked on WHITE");
			} else {
				// empty box was clicked. Do some stuff
				playerMadeMove(i, j);
				comMove();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	private class AI implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == btnReset) {
				reset();
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Othello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// frame.setBounds(50, 50, 500, 200); //placering och storlek på skärmen
		frame.setLocation(50, 50);
		// frame.setSize(500, 200);
		// frame.setResizable(false);
		frame.add(new Othello());
		frame.pack();

	}
}
