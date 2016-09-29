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
 * With this game it is possible to play the board game Othello against a computer. 
 * The computer, or agent, is using alpha-beta pruning search algorithm to determine 
 * its next move. Because it can calculate possible outcomes in near future, it is very hard 
 * for human, if not impossible, to win against this agent.
 * 
 * The board is 4x4, and one can place a piece on whichever empty square.  
 * 
 * @author David Tran & John Tengvall
 * @date 28-09-2016
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
		// Panel East South. Containg RESET-button
		pnlEastSouth.setPreferredSize(new Dimension(200, 100));
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

		btnReset.addActionListener(new AI());//resetbutton
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
				box[i][j].addMouseListener(new ML(i, j)); // adding mouselistener to each panel so it's clickable
				pnlCenter.add(box[i][j]);
			}
		}
		add(pnlEast, BorderLayout.EAST);
		add(pnlCenter, BorderLayout.CENTER);
	}

	/**
	 * This method renders the board of the current state. It also sets the score displayed in labels in GUI.  
	 */
	public void render() {
		int humanScore = 0, comScore = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == EMPTY) { //If the square is empty, set it to green color
					box[i][j].setBackground(new Color(00, 99, 33));
				} else if (state.getOwner(i, j) == HUMAN) {//if the square is the humans square, set it to black
					box[i][j].setBackground(Color.BLACK);
					humanScore++; //human score gets higher after each change
				} else {
					box[i][j].setBackground(Color.WHITE); //if the square is computers square, set it to white. 
					comScore++; //computer score gets higher
				}
			}
		}
		lblHumanScore.setText(humanScore + "");
		lblComScore.setText(comScore + "");
	
	}

	/**
	 * This method resets and restarts the game with a button.
	 */
	public void reset() {
		btnReset.setText("Reset");
		state = new State();
		render();
	}
	/**
	 * First it takes the row and col and prints out in the console where the human put its move. 
	 * It will then call the checkAllDirections() function and see if your move flips any of your 
	 * opponents pieces. Then it calls render() which will render the updated table. 
	 * It will then change the currentplayer to computer. 
	 * @param row row of the placed piece
	 * @param col column of the placed piece
	 */
	public void playerMadeMove(int row, int col) {
		System.out.println("Player put on ROW " + row + " COL " + col);
		checkAllDirections(state, row, col,currentPlayer);
		render();
		currentPlayer *= -1;
	}
	/**
	 * This function is the computers move. First it will call the alphaBetaSearch 
	 * that will calculate the best move and return an index between 0-15 that represents the place on the board.
	 * To get the row the function divide index by 4. 
	 * To get the column it takes the remainder of modulu 4.
	 * After it got the best move it will check where the other players pieces are and then flip the ones in between computers pieces.
	 * Then it will render the board and change player. 
	 */
	public void comMove() {
		ComMove move = new ComMove();
		int index = move.alphaBetaSearch(state);
		int rowMove = index / 4;
		int colMove = index%4;
		checkAllDirections(state, rowMove, colMove,currentPlayer);
		System.out.println("COM put on row " + rowMove + " Col: " + colMove);
		render();
		currentPlayer *= -1;
		
	}
	
	/**
	 * This method calls checkBoard function to check all 8 possible directions.
	 * @param state current state and board
	 * @param placedRow row of the placed piece
	 * @param placedCol column of the placed piece
	 * @param player which player who places the piece, -1 human, 1 computer
	 */
	public void checkAllDirections(State state, int placedRow, int placedCol, int player) {
		int[] dirArray = {0,-1,-1,-1,0,1,1,1,0,-1};//checks west,east,north,south and diagonal from the square. 
		for(int i = 0; i < 8; i++) { //for loop so we see all directions. 
			checkBoard(state, placedRow, placedCol, dirArray[i], dirArray[i+2],player);
		}
	}

	/**
	 * This method places the new piece on the board and checks if any pieces will turn in this specific . 
	 * It then returns a new state with the new board  
	 * @param state The current state 
	 * @param placedRow Row of the placed piece
	 * @param placedCol Column of the placed piece
	 * @param rowDir direction of row, -1 is up, 1 is down, 0 same row
	 * @param colDir direction of column, -1 is left, 1 is right, 0 same column	
	 * @param player which player who places the piece, -1 human, 1 computer
	 * @return state current state and board
	 */
	public State checkBoard(State state, int placedRow, int placedCol, int rowDir, int colDir, int player) {
		int checkRow = placedRow + rowDir; 
		int checkCol = placedCol + colDir;
		int[][] board = state.getBoard();
		boolean foundOpponentPiece = false;
		int pipsToTurn = 0;
		
		board[placedRow][placedCol] = player;
		//Check if we are still looking inside the board. Also if found an empty square, no need to search more,
		//because not possible to turn any pieces.
		while (checkRow >= 0 && checkCol <= 3 && checkCol >= 0 && checkRow <= 3	&& board[checkRow][checkCol] != 0) {
			//if found any of same color and found opponent pieces in between
			if (board[checkRow][checkCol] == player && foundOpponentPiece) {
				//turn pieces in between 
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

		public void mouseClicked(MouseEvent e) {
			JPanel panel = (JPanel) e.getSource();
			if (panel.getBackground().equals(Color.BLACK)) {
				System.out.println("You clicked on black");
			} else if (panel.getBackground().equals(Color.WHITE)) {
				System.out.println("You clicked on WHITE");
			} else {
				playerMadeMove(i, j);
				comMove();
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	private class AI implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnReset) {
				reset();
			}
		}
	}
	/**
	 * Where the program starts. 
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Othello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(50, 50);
		frame.add(new Othello());
		frame.pack();

	}
}
