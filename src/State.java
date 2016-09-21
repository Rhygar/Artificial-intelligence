
public class State {
	
	private final int HUMAN = 1,COM = -1;
	
	
	private int[][] board = {{0,0,0,0},
							 {0,1,-1,0},
							 {0,-1,1,0},
							 {0,0,0,0}
	};
	
	public State(int[][] board) {
		this.board = board;
	}
	
	public State() {
		// TODO Auto-generated constructor stub
	}
	public int getOwner(int row,int col){
		return board[row][col];
	}

	public int[][] getBoard() {
		return this.board;
	}
	
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
	
	public State updateState(State state, int row, int col ){
		this.board = state.getBoard();
		return null;
		
	}
	
	public State playerMove(State state, int row, int col){
		return null;
		
	}
	
	public State comMove(State state, int row, int col){
		State tempBoard = new State(state.getBoard());
		return tempBoard;
	}
	
	
	public State checkBoard(State state, int placedRow, int placedCol,int rowDir, int colDir, int player){
		
		int checkRow = placedRow + rowDir;
		int checkCol = placedCol + colDir;
		int[][] board = state.getBoard();
		board[placedRow][placedCol] = player; 
		boolean foundOpponentPiece = false;
		int pipsToTurn = 0;
		
		
		while(checkRow >= 0 && checkCol <= 3 && checkCol >= 0 && checkRow <= 3 && board[checkRow][checkCol] != 0){
			if(board[checkRow][checkCol] == player && foundOpponentPiece){
				 for(int i = 1; i<=pipsToTurn;i++){
					 board[checkRow-i*rowDir][checkCol-i*colDir] = player;
				 }
				 return new State(board);
			}
			else{ 
				foundOpponentPiece = true;
				pipsToTurn++;
			}
			checkRow += rowDir;
			checkCol += colDir;
		}
		
		return new State(board);
		
	}
}
