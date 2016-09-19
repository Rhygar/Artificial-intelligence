import java.util.Arrays;


public class Test {

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
