public class Team {

	private String name;
	private int wins;
	private int losses;
	private int gamesLeft;
	private int[] gamesToPlayAgainst;
	private int index;

	/**
	 * @param args
	 */
	public Team(String name, int wins, int losses, int left, int ind) {
		this.name = name;
		this.losses = losses;
		this.wins = wins;
		this.gamesLeft = left;
		this.index = ind;
	}

	public String getName() {
		return name;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getGamesLeft() {
		return gamesLeft;
	}

	public int getGamesToPlayAgainst(int ind) {
		return gamesToPlayAgainst[ind];
	}

	public void setGamesToPlayAgainst(int[] gamesToPlayAgainst) {
		this.gamesToPlayAgainst = gamesToPlayAgainst.clone();
	}

	public int getIndex() {
		return index;
	}

}
