/**
 * This class contains the current state of the game. 
 * It consists of a board represented by a two dimensional int array
 * where -1 represents a human piece and +1 represents computer piece. 0 means empty square
 * @author David Tran & John Tengvall
 * date: 28-09-2016
 */

public class State {
	
	private final int HUMAN = -1,COM = 1, EMPTY = 0;
	
	
	private int[][] board = {{0,0,0,0},
							 {0,1,-1,0},
							 {0,-1,1,0},
							 {0,0,0,0}
	};
	
	/**
	 * Constructor with defined board
	 * @param board
	 */
	public State(int[][] board) {
		this.board = board;
	}
	
	/**
	 * Constructor with initial board
	 */
	public State() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method returns the owner of a specific sqaure
	 * @param row row of the sqaure
	 * @param col column of the square
	 * @return The owner. -1 human, +1 com, 0 empty
	 */
	public int getOwner(int row,int col){
		return board[row][col];
	}

	/**
	 * Method returns the board
	 * @return the board as a two dimensional int array
	 */
	public int[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Method sets a board to the state
	 * @param board Two dimensional int array which represents the board
	 */
	public void setBoard(int[][] board) {
		this.board = board;
	}

	/**
	 * Method to see who is the leader. 
	 * Positive means COM is leader, negative return means Human is leader.
	 * Result 0 means tie
	 * @return
	 */
	public int getScore(){
		int score = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] != 0) {
					score += board[i][j];
				}
			}
		}
		return score;
	}
	
	/**
	 * Method sets a square to -1, 1 or 0
	 * @param row row of the square
	 * @param col column of the square
	 * @param player Which player, human, computer or empty 
	 */
	public void updateBoard(int row, int col,int player ){
		this.board[row][col] = player;
		
	}
}
