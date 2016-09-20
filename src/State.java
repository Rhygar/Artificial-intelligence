
public class State {
	
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

	public int getScore(){
		int counter = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] != 0) {
					counter += board[i][j];
				}
			}
		}
		return counter;
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
}
