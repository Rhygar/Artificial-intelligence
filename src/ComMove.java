import java.util.Arrays;

public class ComMove {

	int rowColIndex;
	final int HUMAN = -1, COM = 1, EMPTY = 0;

	public int alphaBetaSearch(State state) {
//		int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
		State tempState = new State(state.getBoard());
		int max = maxValue(tempState, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println(max);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(state.getOwner(i, j));
			}
			System.out.println();
		}
		System.out.println("ROWCOLINDEX = " + rowColIndex);
		return rowColIndex;
	}

//	public int returnIndex(State state) {
//		int[][] board = state.getBoard();
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				if (board[i][j] == 0) {
//					return (i * 4 + j);
//				}
//			}
//		}
//		return 0;
//	}

	public int maxValue(State state, int depth, int a, int b) {
		int alpha = a, beta = b;
		depth--;
		// IF TERMINAL TEST bla bla bla
		System.out.println("Came to MaXValue function");
		if (depth == 0) {
			return state.getScore();
		}
		State tempState = new State(state.getBoard());
		int returnValue = Integer.MIN_VALUE;
		
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tempState.getOwner(i, j) == 0) {
					tempState = checkAllDirections(state,i,j,COM);
					returnValue = Math.max(
							returnValue, minValue(tempState, depth, alpha, beta)); // change new State to call result method
					if (returnValue >= beta) {
						return returnValue;
					}
					alpha = Math.max(alpha, returnValue);
					System.out.println("ALPHA = " + alpha);
					// when we set alpha, we know this was the best way. Update
					// row,col to move
					if (returnValue > alpha) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
				}
			}
		}

		return returnValue;
	}

	public int minValue(State state, int depth, int alpha, int beta) {
		int alpha2 = alpha, beta2 = beta;
		depth--;
		System.out.println("Came to Min Value function");
		// IF TERMINAL TEST bla bla bla
		if (depth == 0) {
			return state.getScore();
		}
		State tempState = new State(state.getBoard());
		int returnValue = Integer.MAX_VALUE;
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tempState.getOwner(i, j) == 0) {
					tempState = checkAllDirections(tempState,i,j,HUMAN);
					returnValue = Math.min(
							returnValue,
							maxValue(tempState,depth, alpha2, beta2)); // change newState to call result method
					if (returnValue <= alpha) {
						return returnValue;
					}
					beta = Math.min(beta, returnValue);
					System.out.println("BETA = " + beta);
					if (returnValue < beta) {
						System.out.println("RowColIndex UPDATED");
						rowColIndex = (i * 4 + j);
					}
				}
			}
		}

		return returnValue;
	}

	public State checkAllDirections(State state, int placedRow, int placedCol,
			int player) {
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		int[][] tempBoard = state.getBoard();
		State tempState = new State(tempBoard);
		for (int i = 0; i < 8; i++) {
			tempState = checkBoard(tempState, placedRow, placedCol, dirArray[i],
					dirArray[i + 2], player);
		}
		return tempState;
	}

	public State checkBoard(State state, int placedRow, int placedCol,
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
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(state.getOwner(i, j));
			}
			System.out.println();
		}
		System.out.println();
		return state;
	}

}
