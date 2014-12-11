import java.util.Comparator;
import java.util.TreeSet;

public class Solver {

	private class PriorityComparator implements Comparator<Container> {
		@Override
		public int compare(Container a, Container b) {
			return a.getPriority() - b.getPriority();
		}
	}

	private class BoardComparator implements Comparator<Board> {
		@Override
		public int compare(Board x, Board y) {
			if (x.equals(y))
				return 0;
			return 1;
		}
	}

	private class Container implements Comparable<Container> {
		public Container(Board b) {
			board = b;
			parent = null;
			moves = 0;
		}

		public Container(Board b, Container parent, int moves) {
			this.board = b;
			this.parent = parent;
			this.moves = moves;
		}

		public Board board;

		public Container parent;

		public int moves;

		public int getPriority() {
			return board.manhattan() + moves;
		}

		@Override
		public int compareTo(Container y) {
			if (y == this)
				return 0;
			if (y == null)
				return 1;
			if (board.equals(y.board)) {
				return 0;
			}
			return 1;
		}
	}

	private MinPQ<Container> origPQ;
	private MinPQ<Container> twinPQ;
	private boolean isSolvable;

	private Container origSolutionBoard;
	private Container twinSolutionBoard;

	// private TreeSet<Board> origProcessedBoards;
	// private TreeSet<Board> twinProcessedBoards;

	public Solver(Board initial) // find a solution to the initial board (using
									// the A* algorithm)
	{
		origSolutionBoard = new Container(initial);
		twinSolutionBoard = new Container(initial.twin());

		origPQ = new MinPQ<Container>(new PriorityComparator());
		origPQ.insert(origSolutionBoard);
		twinPQ = new MinPQ<Container>(new PriorityComparator());
		twinPQ.insert(twinSolutionBoard);

		// origProcessedBoards = new TreeSet<Board>(new BoardComparator());
		// origProcessedBoards.add(origSolutionBoard.board);
		// twinProcessedBoards = new TreeSet<Board>(new BoardComparator());
		// twinProcessedBoards.add(twinSolutionBoard.board);

		isSolvable = solve();
	}

	private boolean solve() {
		while (true) {
			if (origSolutionBoard.board.isGoal()) {
				return true;
			}

			if (twinSolutionBoard.board.isGoal()) {
				return false;
			}

			for (Board neighbor : origSolutionBoard.board.neighbors()) {
				if (origSolutionBoard.parent != null
						&& origSolutionBoard.parent.board.equals(neighbor)) {
					continue;
				}

				Container c = new Container(neighbor, origSolutionBoard,
						origSolutionBoard.moves + 1);
				origPQ.insert(c);

				// if(!origProcessedBoards.contains(c.board))
				// {
				// origPQ.insert(c);
				// origProcessedBoards.add(c.board);
				// }
			}

			for (Board neighbor : twinSolutionBoard.board.neighbors()) {
				if (twinSolutionBoard.parent != null
						&& twinSolutionBoard.parent.board.equals(neighbor)) {
					continue;
				}

				Container c = new Container(neighbor, twinSolutionBoard,
						twinSolutionBoard.moves + 1);
				twinPQ.insert(c);

				// if(!twinProcessedBoards.contains(c.board))
				// {
				// twinPQ.insert(c);
				// twinProcessedBoards.add(c.board);
				// }
			}

			origSolutionBoard = origPQ.delMin();
			twinSolutionBoard = twinPQ.delMin();

		}

	}

	public boolean isSolvable() // is the initial board solvable?
	{
		return isSolvable;
	}

	public int moves() // min number of moves to solve initial board; -1 if no
						// solution
	{
		if (!isSolvable) {
			return -1;
		}
		return origSolutionBoard.moves;
	}

	public Iterable<Board> solution() // sequence of boards in a shortest
										// solution; null if no solution
	{
		if (!isSolvable) {
			return null;
		}

		Stack<Board> solutionBoards = new Stack<Board>();
		Container b = origSolutionBoard;
		while (b != null) {
			solutionBoards.push(b.board);
			b = b.parent;
		}
		return solutionBoards;
	}

	public static void main(String[] args) // solve a slider puzzle (given
											// below)
	{
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

}
