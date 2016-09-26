import java.util.Arrays;

public class ComMove {

	int rowColIndex;
	final int HUMAN = -1, COM = 1, EMPTY = 0;

	public int alphaBetaSearch(State state) {
		State tempState = new State(state.getBoard());
		int max = maxValue(tempState, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println(max);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(state.getOwner(i, j));
			}
			System.out.println();
		}
		return 15;
	}

	public int returnIndex(State state) {
		int[][] board = state.getBoard();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0) {
					return (i * 4 + j);
				}
			}
		}
		return 0;
	}

	public int maxValue(State state, int depth, int alpha, int beta) {
		// IF TERMINAL TEST bla bla bla
		System.out.println("Came to MaXValue function");
		if (depth == 0) {
			return state.getScore();
		}
		int returnValue = Integer.MIN_VALUE;
		
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = new State(checkAllDirections(state,i,j,COM).getBoard());
					returnValue = Math.max(
							returnValue, minValue(tempState, depth - 1, alpha, beta)); // change new State to call result method
					if (returnValue >= beta) {
						return returnValue;
					}
					alpha = Math.max(alpha, returnValue);
					// when we set alpha, we know this was the best way. Update
					// row,col to move
					if (returnValue > alpha) {
						rowColIndex = (i * 4 + j);
					}
				}
			}
		}

		return returnValue;
	}

	public int minValue(State state, int depth, int alpha, int beta) {
		System.out.println("Came to Min Value function");
		// IF TERMINAL TEST bla bla bla
		if (depth == 0) {
			return state.getScore();
		}
		int returnValue = Integer.MAX_VALUE;
		// check all possible moves - where there are zeroes
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state.getOwner(i, j) == 0) {
					State tempState = new State(checkAllDirections(state,i,j,HUMAN).getBoard());
					returnValue = Math.min(
							returnValue,
							maxValue(tempState,depth - 1, alpha, beta)); // change newState to call result method
					if (returnValue <= alpha) {
						return returnValue;
					}
					beta = Math.min(beta, returnValue);
				}
			}
		}

		return returnValue;
	}

	public State checkAllDirections(State state, int placedRow, int placedCol,
			int player) {
		int[] dirArray = { 0, -1, -1, -1, 0, 1, 1, 1, 0, -1 };
		State tempState = new State(state.getBoard());
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
		return state;
	}

}
