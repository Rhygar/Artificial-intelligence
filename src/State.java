
public class State {
	
	private int[][] state = {{0,0,0,0},
							 {0,1,2,0},
							 {0,2,1,0},
							 {0,0,0,0}
	};
	
	public State(int[][] state) {
		this.state = state;
	}
	
	public State() {
		// TODO Auto-generated constructor stub
	}
	public int getOwner(int row,int col){
		return state[row][col];
	}

	public int[][] getState() {
		return this.state;
	}
	
	public void setState(int[][] state) {
		this.state = state;
	}

	public int getScore(){
		int playerScore = 0;
		int comScore = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(state[i][j] == 1){
					playerScore++;
				}
				else if(state[i][j] == 2){
					comScore++;
				}
			}
		}
		return comScore - playerScore;
	}
	
}
