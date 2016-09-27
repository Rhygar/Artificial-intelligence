import java.util.Arrays;

public class ComMove {
	int rowColIndex;
	final int HUMAN = -1, COM = 1, EMPTY = 0;

	public int alphaBetaSearch(State state) {
//		int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
		State tempState = new State(state.getBoard());
		int max = maxValue(tempState, 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return rowColIndex;
	}

	public int maxValue(State state, int depth, int a, int b) {
		int alpha = a, beta = b;
		depth--;
		// IF TERMINAL TEST bla bla bla
		System.out.println("Came to MaXValue function");
		if (depth < 1) {
			return state.getScore();
		}
//		State tempState = new State(state.getBoard().clone());
		int returnValue = Integer.MIN_VALUE;
		
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = new State();
					for(int k = 0; k < 4; k++) {
						for(int m = 0; m < 4; m++) {
							tempState.updateBoard(k, m, state.getOwner(k, m));
						}
					}
					checkAllDirections(tempState,i,j,COM);
					returnValue = Math.max(
							returnValue, minValue(tempState, depth, alpha, beta)); // change new State to call result method
					if (returnValue >= b) {
						return returnValue;
					}
					a = Math.max(a, returnValue);
					System.out.println("ALPHA = " + alpha);
					// when we set alpha, we know this was the best way. Update
					// row,col to move
					if (returnValue > a) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
				}
			}
		}

		return returnValue;
	}

	public int minValue(State state, int depth, int a, int b) {
		int alpha = a, beta = b;
		depth--;
		System.out.println("Came to Min Value function");
		// IF TERMINAL TEST bla bla bla
		if (depth < 1) {
			return state.getScore();
		}
//		State tempState = new State(state.getBoard().clone());
		int returnValue = Integer.MAX_VALUE;
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println("Came to Min Value after for");
				if (state.getOwner(i, j) == 0) {
					State tempState = new State();
					for(int k = 0; k < 4; k++) {
						for(int m = 0; m < 4; m++) {
							tempState.updateBoard(k, m, state.getOwner(k, m));
						}
					}
					checkAllDirections(tempState,i,j,HUMAN);
					returnValue = Math.min(
							returnValue,
							maxValue(tempState,depth, alpha, beta)); // change newState to call result method
					if (returnValue <= a) {
						return returnValue;
					}
					b = Math.min(b, returnValue);
					System.out.println("BETA = " + beta);
					if (returnValue < b) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
				}
			}
		}

		return returnValue;
	}

	public static State checkAllDirections(State state, int placedRow, int placedCol,
			int player) {
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		int[][] tempBoard = state.getBoard().clone();
		State tempState = new State(tempBoard);
		for (int i = 0; i < 8; i++) {
			tempState = checkBoard(tempState, placedRow, placedCol, dirArray[i],
					dirArray[i + 2], player);
		}
		return tempState;
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
	
	public static void main(String[] args) {
		int[][] testBoard = {{0,0,0,0},
							 {0,-1,1,0},
							 {0,1,-1,0},
							 {0,0,0,0}
		};
		State state = new State(testBoard);
		
		State newState = new State();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				newState.updateBoard(i, j, state.getOwner(i, j));
			}
		}
		
		
				
		checkAllDirections(newState, 1,0,1);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(newState.getOwner(i, j));
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(state.getOwner(i, j));
			}
			System.out.println();
		}
	}

}
