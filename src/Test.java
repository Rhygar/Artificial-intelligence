import java.util.Arrays;


public class Test {
	
	
	public int alphaBetaSearch(State state) {
		int action = maxValue(state,0,Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		return 0;
	}
	
	public int maxValue(State state,int depth, int alpha, int beta) {
		//IF TERMINAL TEST bla bla bla
		
		int returnValue = Integer.MIN_VALUE;
		int[][] board = state.getBoard();
		//check all possible moves - where there are zeroes
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == 0) {
					returnValue = Math.max(returnValue, minValue(new State(),depth + 1,alpha,beta) );  //change new State to call result method
					if(returnValue >= beta) {
						return returnValue;
					}
					alpha = Math.max(alpha, returnValue);
				}
			}
		}
		
		return returnValue;
	}
	
	public int minValue(State state,int depth, int alpha, int beta) {
		//IF TERMINAL TEST bla bla bla
		int returnValue = Integer.MAX_VALUE;
		int[][] board = state.getBoard();
		//check all possible moves - where there are zeroes
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == 0) {
					returnValue = Math.min(returnValue, maxValue(new State(),depth + 1,alpha,beta));  //change new State to call result method
					if(returnValue <= alpha) {
						return returnValue;
					}
					beta = Math.min(beta, returnValue);
				}
			}
		}
		
		return returnValue;
	}
	
	public static int sum(int a, int b) {
		a = a + 1;
		return a;
	}

	public static void main (String[] args) {
		int[][] a = {
				{1,1,1,1},
				{1,1,1,1},
				{1,1,1,1},
				{1,1,1,1}
		};
		int[][] b = {
				{1,1,1,1},
				{1,1,1,1},
				{1,1,1,1},
				{1,1,1,1}
		};
		
		State aa = new State(a);
		State bb = new State(b);
		
		if(Arrays.deepEquals(a,b)) {
			System.out.println("Same same");
		} else {
			System.out.println("NOT same");
		}
	}
}
