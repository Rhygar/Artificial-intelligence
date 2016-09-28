
public class ComMove {
	
	int depth;
	final int HUMAN = -1, COM = 1, EMPTY = 0;
	int rowColIndex;
	int nodesChecked = 0;

	
	public int alphaBetaSearch(State state) {
		maxValue(state,8,Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		System.out.println("Nodes checked: " + nodesChecked);
		return rowColIndex;
	}
	
	public int maxValue(State state,int depth, int a, int b, boolean allowChangeAlpha) {
		nodesChecked++;
		int currentEmptySlot = 0;
		depth--;

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					currentEmptySlot++;
				}
			}
		}
		if (depth < 1 || currentEmptySlot == 0) {
			return state.getScore();
		}
		int returnValue = Integer.MIN_VALUE;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = getNewState(state,i,j,COM);
					returnValue = Math.max(returnValue, minValue(tempState,depth,a, b)); // change new State to call result method
					if (returnValue >= b) {
						depth++;
						System.out.println("Pruning on max");
						return returnValue;
					}
					if (returnValue > a && allowChangeAlpha) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
					a = Math.max(a, returnValue);
				}
			}
		}
		depth++;
		return returnValue;
	}
	
	public int minValue(State state,int depth, int a, int b) {
		nodesChecked++;
		int returnValue = Integer.MAX_VALUE;
		depth--;
		int currentEmptySlot = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					currentEmptySlot ++;
				}
			}
		}
		if (depth < 1 || currentEmptySlot == 0) {
			return state.getScore();
		}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = getNewState(state,i,j,HUMAN);
					returnValue = Math.min(returnValue,maxValue(tempState,depth,a, b, false)); // change newState to call result method
					if (returnValue <= a) {
						depth++;
						System.out.println("Pruning on min");
						return returnValue;
					}
					b = Math.min(b, returnValue);
				}
			}
		}
		depth++;
		return returnValue;
	}
	
	public static State getNewState(State state, int placedRow, int placedCol, int player) {
		State tempState = new State();
		for(int k = 0; k < 4; k++) {
			for(int m = 0; m < 4; m++) {
				tempState.updateBoard(k, m, state.getOwner(k, m));
			}
		}
		
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		for (int i = 0; i < 8; i++) {
			checkBoard(tempState, placedRow, placedCol, dirArray[i], dirArray[i + 2], player);
		}
		return tempState;
	}

	public static State checkBoard(State state, int placedRow, int placedCol, int rowDir, int colDir, int player) {

		int checkRow = placedRow + rowDir;
		int checkCol = placedCol + colDir;
		int[][] board = state.getBoard();
		boolean foundOpponentPiece = false;
		int pipsToTurn = 0;

		board[placedRow][placedCol] = player;

		while (checkRow >= 0 && checkCol <= 3 && checkCol >= 0 && checkRow <= 3
				&& board[checkRow][checkCol] != 0) {
			// if found any of same color and found opponent pieces in between
			if (board[checkRow][checkCol] == player && foundOpponentPiece) {
				for (int i = 1; i <= pipsToTurn; i++) {
					board[checkRow - i * rowDir][checkCol - i * colDir] = player;
				}
				 state.setBoard(board);
				return state;
			} else if (!foundOpponentPiece
					&& board[checkRow][checkCol] == player) {
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

}
