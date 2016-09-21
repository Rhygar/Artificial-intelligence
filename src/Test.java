import java.util.Arrays;


public class Test {
	
	public int alphaBetaSearch(State state) {
		int action = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		return 0;
	}
	
	public int maxValue(State state, int a, int b) {
		//IF TERMINAL TEST bla bla bla
		
		int returnValue = Integer.MIN_VALUE;
		int[][] board = state.getBoard();
		//check all possible moves - where there are zeroes
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == 0) {
					returnValue = Math.max(returnValue, minValue(new State(),a,b));  //change new State to call result method
					if(returnValue >= b) {
						return returnValue;
					}
					a = Math.max(a, returnValue);
				}
			}
		}
		
		return returnValue;
	}
	
	public int minValue(State state, int a, int b) {
		//IF TERMINAL TEST bla bla bla
		int returnValue = Integer.MAX_VALUE;
		int[][] board = state.getBoard();
		//check all possible moves - where there are zeroes
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == 0) {
					returnValue = Math.min(returnValue, maxValue(new State(),a,b));  //change new State to call result method
					if(returnValue <= a) {
						return returnValue;
					}
					b = Math.min(b, returnValue);
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
		
		int x = 8;
		x = sum(x,0);
		System.out.println(x);
	
	}
}
