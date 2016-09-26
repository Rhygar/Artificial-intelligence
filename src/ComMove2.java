
public class ComMove2 {
	
	int depth = 0;
	final int HUMAN = -1, COM = 1, EMPTY = 0;
	int rowColIndex, startDjup = 0;
	final int DJUP = 0;
	
	public int alphaBetaSearch(State state) {
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					startDjup++;
				}
			}
		}
		maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return rowColIndex;
	}
	
	public int maxValue(State state, int a, int b) {
		int alpha = a, beta = b;
		int daDepth = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					daDepth++;
				}
			}
		}
		if((startDjup - daDepth) > DJUP) {
			return state.getScore();
		}
		int returnValue = Integer.MIN_VALUE;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					System.out.println("Now checking row: " + i + " Col: " + j);
					State tempState = new State();
					for(int k = 0; k < 4; k++) {
						for(int m = 0; m < 4; m++) {
							tempState.updateBoard(k, m, state.getOwner(k, m));
						}
					}
					
					checkAllDirections(tempState,i,j,COM);
					returnValue = Math.max(returnValue, minValue(tempState,alpha, beta)); // change new State to call result method
					if (returnValue >= b) {
						return returnValue;
					}
					if (returnValue > a) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
					a = Math.max(a, returnValue);
					System.out.println("ALPHA = " + a);
					// when we set alpha, we know this was the best way. Update
					// row,col to move
					
				}
			}
		}
		
		
		
		return returnValue;
	}
	
	public int minValue(State state, int a, int b) {
		int daDepth = 0;
		int alpha = a, beta = b;
		int returnValue = Integer.MAX_VALUE;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(state.getOwner(i, j) == 0) {
					daDepth++;
				}
			}
		}
		if((startDjup - daDepth) > DJUP) {
			return state.getScore();
		}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
//				System.out.println("Came to Min Value after for");
				if (state.getOwner(i, j) == 0) {
					State tempState = new State();
					for(int k = 0; k < 4; k++) {
						for(int m = 0; m < 4; m++) {
							tempState.updateBoard(k, m, state.getOwner(k, m));
						}
					}
					checkAllDirections(tempState,i,j,HUMAN);
					returnValue = Math.min(returnValue,maxValue(tempState, alpha, beta)); // change newState to call result method
					if (returnValue <= a) {
						return returnValue;
					}
					if (returnValue < b) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
					b = Math.min(b, returnValue);
					System.out.println("BETA = " + b);
					
				}
			}
		}
		
		
		return returnValue;
	}
	
	public static State checkAllDirections(State state, int placedRow, int placedCol,
			int player) {
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		for (int i = 0; i < 8; i++) {
			checkBoard(state, placedRow, placedCol, dirArray[i],
					dirArray[i + 2], player);
		}
		return state;
	}

	public static State checkBoard(State state, int placedRow, int placedCol,
			int rowDir, int colDir, int player) {

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
//		for(int i = 0; i < 4; i++) {
//			for(int j = 0; j < 4; j++) {
//				System.out.print(state.getOwner(i, j));
//			}
//			System.out.println();
//		}
//		System.out.println();
		return state;
	}

}
