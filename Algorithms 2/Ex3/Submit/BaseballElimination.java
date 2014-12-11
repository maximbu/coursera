public class BaseballElimination {

	private static double inf = Double.POSITIVE_INFINITY;
	private int numOfTeams;
	private Team[] teams;
	private FordFulkerson solver;
	private int lastTeamIndex = -1;

	public BaseballElimination(String filename) {
		// create a baseball division from given filename in format specified
		// below
		parseData(filename);
	}

	private void parseData(String file) {
		In in = new In(file);
		numOfTeams = in.readInt();
		teams = new Team[numOfTeams];
		for (int i = 0; i < numOfTeams; i++) {
			String name = in.readString();
			int wins = in.readInt();
			int losses = in.readInt();
			int gamesLeft = in.readInt();
			teams[i] = new Team(name, wins, losses, gamesLeft, i);
			teams[i].setGamesToPlayAgainst(readTeamGames(in));
		}
	}

	private int[] readTeamGames(In in) {
		int[] games = new int[numOfTeams];
		for (int j = 0; j < numOfTeams; j++) {
			games[j] = in.readInt();
		}
		return games;
	}

	public int numberOfTeams() {
		// number of teams
		return numOfTeams;
	}

	public Iterable<String> teams() {
		// all teams
		Queue<String> teamNames = new Queue<String>();

		for (Team t : teams) {
			teamNames.enqueue(t.getName());
		}
		return teamNames;
	}

	public int wins(String team) {
		// number of wins for given team
		Team t = getTeamIfValid(team);
		return t.getWins();
	}

	public int losses(String team) {
		// number of losses for given team
		Team t = getTeamIfValid(team);
		return t.getLosses();
	}

	public int remaining(String team) {
		// number of remaining games for given team
		Team t = getTeamIfValid(team);
		return t.getGamesLeft();
	}

	public int against(String team1, String team2) {
		// number of remaining games between team1 and team2
		Team t1 = getTeamIfValid(team1);
		Team t2 = getTeamIfValid(team2);

		return t1.getGamesToPlayAgainst(t2.getIndex());
	}

	public boolean isEliminated(String team) {
		// is given team eliminated?
		Team t = getTeamIfValid(team);
		if (trivialElimination(t) != null) {
			return true;
		}
		return nonTrivialElimination(t);
	}

	private boolean nonTrivialElimination(Team t) {
		buildSolver(t);
		return checkIfEliminated();
	}

	private int getGamesToPlay() {
		int n = teams.length - 1;
		return (n * (n - 1)) / 2;
	}

	private void buildSolver(Team t) {
		int n = teams.length - 1;
		int gamesToPlay = getGamesToPlay();
		if (lastTeamIndex == t.getIndex()) {
			return;
		}

		lastTeamIndex = t.getIndex();
		int V = 1 + gamesToPlay + n + 1;
		int maxPossibleWins = t.getGamesLeft() + t.getWins();
		FlowNetwork network = new FlowNetwork(V);
		int k = 1;
		for (int i = 1; i <= n; i++) {
			int teamInd = i - 1;
			if (teamInd == lastTeamIndex)
				continue;
			for (int j = teamInd + 1; j <= n; j++) {
				if (j == lastTeamIndex)
					continue;

				int newVertixInd = k++;
				FlowEdge e = new FlowEdge(0, newVertixInd,
						teams[teamInd].getGamesToPlayAgainst(j));
				network.addEdge(e);
				FlowEdge e1 = new FlowEdge(newVertixInd, getVertixIndexOfTeam(
						teamInd, lastTeamIndex), inf);
				network.addEdge(e1);
				FlowEdge e2 = new FlowEdge(newVertixInd, getVertixIndexOfTeam(
						j, lastTeamIndex), inf);
				network.addEdge(e2);
			}
		}

		for (int i = 0; i < teams.length; i++) {
			if (i == lastTeamIndex)
				continue;
			FlowEdge e = new FlowEdge(getVertixIndexOfTeam(i, lastTeamIndex),
					V - 1, maxPossibleWins - teams[i].getWins());
			network.addEdge(e);
		}
		// Out out = new Out();
		// out.println(network.toString());
		solver = new FordFulkerson(network, 0, V - 1);
	}

	private int getVertixIndexOfTeam(int teamInd, int teamInQuestionIndex) {
		int modifier = 0;
		if (teamInQuestionIndex > teamInd) {
			modifier = 1;
		}
		int gamesToPlay = getGamesToPlay();
		return gamesToPlay + teamInd + modifier;
	}

	private boolean checkIfEliminated() {
		int n = teams.length - 1;
		int gamesToPlay = (n * (n - 1)) / 2;
		for (int i = 1; i < gamesToPlay; i++) {
			if (solver.inCut(i))
				return true;
		}

		return false;
	}

	private Team trivialElimination(Team t) {
		int maxPossibleWins = t.getGamesLeft() + t.getWins();
		for (Team opponent : teams) {
			if (opponent.getWins() > maxPossibleWins)
				return opponent;
		}
		return null;
	}

	public Iterable<String> certificateOfElimination(String team) {
		// subset R of teams that eliminates given team; null if not eliminated
		Team t = getTeamIfValid(team);
		Queue<String> teamNames = new Queue<String>();
		Team trivialOpponent = trivialElimination(t);
		if (trivialOpponent != null) {
			teamNames.enqueue(trivialOpponent.getName());
			return teamNames;
		}

		buildSolver(t);

		for (int i = 0; i < teams.length; i++) {
			if (i != lastTeamIndex
					&& solver.inCut(getVertixIndexOfTeam(i, lastTeamIndex))) {
				teamNames.enqueue(teams[i].getName());
			}
		}
		if(teamNames.size()>1)
			return teamNames;
		return null;
	}

	private Team getTeam(String team) {
		for (Team t : teams) {
			if (t.getName().equals(team)) {
				return t;
			}
		}
		return null;
	}

	private Team getTeamIfValid(String team) {
		Team t = getTeam(team);
		if (t == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return t;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:\\Studies\\Algo2\\Ex3\\baseball-testing\\baseball\\teams5.txt";
		BaseballElimination division = new BaseballElimination(filePath);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team))
					StdOut.print(t + " ");
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}
