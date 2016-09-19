
public class State {
	
	private int[][] state = new int[4][4];
	
	public State(int[][] state) {
		this.state = state;
	}
	
	public int[][] getState() {
		return this.state;
	}
	
	public void setState(int[][] state) {
		this.state = state;
	}

}
