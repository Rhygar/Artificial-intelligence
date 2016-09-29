/**
 * This class represents a Computer Move for the AI. 
 * It implements an alpha beta search algorithm to determine which move is
 * the best move for the AI to use. 
 * @author David Tran & John Tengvall
 * @date 28-09-2016
 *
 */
public class ComMove {
	
	private int rowColIndex, nodesChecked = 0, currentMaxDepth = 0;
	final int HUMAN = -1, COM = 1, EMPTY = 0, DEPTH = 8;

	/**
	 * This method uses the alpha beta search algorithm to determine the best move.  
	 * @param state The current state of the board
	 * @return an integer between 0-15 which represents the index of the board, where 0 is in the upper left corner,
	 * and 15 is the lower right, numerated from left to right.  
	 */
	public int alphaBetaSearch(State state) {
		maxValue(state,0,Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		System.out.println("Nodes checked: " + nodesChecked);
		System.out.println("Depth search: " + currentMaxDepth);
		return rowColIndex;
	}
	
	public int maxValue(State state,int depth, int a, int b, boolean allowChangeAlpha) {
		nodesChecked++;
		int currentEmptySlot = 0;
		depth++;
		currentMaxDepth = Math.max(currentMaxDepth, depth);
		
		//Count how many zeroes on the board. If result is 0, then a leafnode is reached
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					currentEmptySlot++;
				}
			}
		}
		
		//check if a leaf node is reached or depth limit reached
		if (depth == DEPTH || currentEmptySlot == 0) {
			return state.getScore();
		}
		int returnValue = Integer.MIN_VALUE;
		//for each action, i.e. all empty squares on the board
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = getNewState(state,i,j,COM);
					returnValue = Math.max(returnValue, minValue(tempState,depth,a, b)); // change new State to call result method
					//if returnvalue is larger than beta, no need to search more, prune
					if (returnValue >= b) {
						//increase depth when going up to parent
						depth--;
						System.out.println("Pruning on max");
						return returnValue;
					}
					//only in root node, we are allowed to change the rowColIndex
					if (returnValue > a && allowChangeAlpha) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
					//update alpha
					a = Math.max(a, returnValue);
				}
			}
		}
		//increase depth when going up to parent
		depth--;
		return returnValue;
	}
	
	/*
	 * Only difference between this method and maxValue is this method updates the beta instead of alpha
	 * and prunes when returnValue is equal or smaller than alpha. 
	 */
	public int minValue(State state,int depth, int a, int b) {
		nodesChecked++;
		int returnValue = Integer.MAX_VALUE;
		depth++;
		currentMaxDepth = Math.max(currentMaxDepth, depth);
		int currentEmptySlot = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					currentEmptySlot ++;
				}
			}
		}
		if (depth == DEPTH || currentEmptySlot == 0) {
			return state.getScore();
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = getNewState(state,i,j,HUMAN);
					returnValue = Math.min(returnValue,maxValue(tempState,depth,a, b, false)); // change newState to call result method
					//if returnvalue is larger than beta, no need to search more, prune
					if (returnValue <= a) {
						depth--;
						System.out.println("Pruning on min");
						return returnValue;
					}
					b = Math.min(b, returnValue);
				}
			}
		}
		depth--;
		return returnValue;
	}
	
	/**
	 * This method receives a state along with a row and col where the player puts a piece.
	 * It then checks which pieces will be turned in all directions, and returns a new state with an 
	 * updated board
	 * @param state	the current state before placing a piece
	 * @param placedRow the row of where the piece is to be placed
	 * @param placedCol the column of where the piece is to be placed
	 * @param player which player who is putting the piece, -1 for human, +1 for COM	
	 * @return a new state with an updated board
	 */
	public static State getNewState(State state, int placedRow, int placedCol, int player) {
		//create a new state and copy all info from state to the tempState
		State tempState = new State();
		for(int k = 0; k < 4; k++) {
			for(int m = 0; m < 4; m++) {
				tempState.updateBoard(k, m, state.getOwner(k, m));
			}
		}
		
		//as we want to check all 8 possible directions where player can flip pieces, we use a loop to call 
		//checkBoard() 8 times with different directions
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		for (int i = 0; i < 8; i++) {
			checkBoard(tempState, placedRow, placedCol, dirArray[i], dirArray[i + 2], player);
		}
		return tempState;
	}

	/**
	 * This method places the new piece on the board and checks if any pieces will turn in this specific direction. 
	 * It then returns a new state with the new board, but also manipulates the state which is received as argument.   
	 * @param state The current state 
	 * @param placedRow Row of the placed piece
	 * @param placedCol Column of the placed piece
	 * @param rowDir direction of row, -1 is up, 1 is down, 0 same row
	 * @param colDir direction of column, -1 is left, 1 is right, 0 same column	
	 * @param player which player who places the piece, -1 human, 1 computer
	 * @return the updated state
	 */
	public static State checkBoard(State state, int placedRow, int placedCol, int rowDir, int colDir, int player) {

		int checkRow = placedRow + rowDir;
		int checkCol = placedCol + colDir;
		int[][] board = state.getBoard();
		boolean foundOpponentPiece = false;
		int pipsToTurn = 0;

		board[placedRow][placedCol] = player;

		//check that we are still looking inside the board. Also if found an empty square, no need to search more,
		//because not possible to turn any pieces
		while (checkRow >= 0 && checkCol <= 3 && checkCol >= 0 && checkRow <= 3	&& board[checkRow][checkCol] != 0) {
			// if found any of same color and found opponent pieces in between
			if (board[checkRow][checkCol] == player && foundOpponentPiece) {
				//turn pieces in between
				for (int i = 1; i <= pipsToTurn; i++) {
					board[checkRow - i * rowDir][checkCol - i * colDir] = player;
				}
				 state.setBoard(board);
				return state;
			// same color found without founded any of the opponents pieces. 
			} else if (!foundOpponentPiece && board[checkRow][checkCol] == player) {
				break;
			} else {
				foundOpponentPiece = true;
				pipsToTurn++;
			}
			//move along the direction
			checkRow += rowDir;
			checkCol += colDir;
		}
		//update the board
		state.setBoard(board);
		return state;
	}

}
