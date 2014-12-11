public class Board{

	private int[][] tiles;
	private int N;
	private int hamming;
	private int manhattan;

	public Board(int[][] blocks) // construct a board from an N-by-N array of
									// blocks
	// (where blocks[i][j] = block in row i, column j)
	{
		N = blocks.length;
		tiles = copyArray(blocks);
		hamming = -1;
		manhattan = -1;
	}
	
	private int[][] copyArray(int[][] orig)
	{
		int[][] newArray = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				newArray[i][j] = orig[i][j];
			}
		
		return newArray;
	}
			

	public int dimension() // board dimension N
	{
		return N;
	}

	public int hamming() // number of blocks out of place
	{
		if(hamming == -1)
		{
		hamming = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				int currentNum = tiles[i][j];
				if (currentNum != 0 && currentNum != correctOrder(i, j)) {
					hamming++;
				}
			}
		}
		return hamming;
	}

	private int correctOrder(int i, int j) {

		return (i * N) + j + 1;
	}

	public int manhattan() // sum of Manhattan distances between blocks and goal
	{
		if(manhattan == -1)
		{
		manhattan = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				int currentNum = tiles[i][j];
				if (currentNum != 0) {
					manhattan += numberOfMoves(currentNum, i, j);
				}
			}
		}
		
		return manhattan;
	}

	private int numberOfMoves(int currentNum, int i, int j) {
		if (currentNum == correctOrder(i, j)) {
			return 0;
		}
		int correctI = (currentNum - 1) / N;
		int correctJ = (currentNum - 1) % N;
		return Math.abs(i - correctI) + Math.abs(j - correctJ);
	}

	public boolean isGoal() // is this board the goal board?
	{
		return hamming() == 0;
	}

	public Board twin() // a board obtained by exchanging two adjacent blocks in
						// the same row
	{
		int[][] blocks = copyArray(tiles);

		int i = 0;
		if (blocks[0][0] == 0 || blocks[0][1] == 0) {
			i = 1;
		}
		swap(blocks, i, 0, i, 1);

		return new Board(blocks);
	}

	private void swap(int[][] blocks, int i1, int j1, int i2, int j2) {
		int temp;
		temp = blocks[i1][j1];
		blocks[i1][j1] = blocks[i2][j2];
		blocks[i2][j2] = temp;
	}

	public boolean equals(Object y) // does this board equal y?
	{
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if (N != that.N)
			return false;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] != that.tiles[i][j])
					return false;
			}
		return true;
	}

	public Iterable<Board> neighbors() // all neighboring boards
	{
		Stack<Board> neighbors = new Stack<Board>();

		int zeroI = 0;
		int zeroJ = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == 0) {
					zeroI = i;
					zeroJ = j;
					break;
				}
			}

		int[][] blocks = copyArray(tiles);
		
		if (zeroI != 0) {
			swap(blocks, zeroI, zeroJ, zeroI - 1, zeroJ);
			neighbors.push(new Board(blocks));
			swap(blocks, zeroI, zeroJ, zeroI - 1, zeroJ);
		}

		if (zeroI != N - 1) {
			swap(blocks, zeroI, zeroJ, zeroI + 1, zeroJ);
			neighbors.push(new Board(blocks));
			swap(blocks, zeroI, zeroJ, zeroI + 1, zeroJ);
		}

		if (zeroJ != 0) {
			swap(blocks, zeroI, zeroJ, zeroI, zeroJ - 1);
			neighbors.push(new Board(blocks));
			swap(blocks, zeroI, zeroJ, zeroI, zeroJ - 1);
		}

		if (zeroJ != N - 1) {
			swap(blocks, zeroI, zeroJ, zeroI, zeroJ + 1);
			neighbors.push(new Board(blocks));
			swap(blocks, zeroI, zeroJ, zeroI, zeroJ + 1);
		}

		return neighbors;
	}

	public String toString() // string representation of the board (in the
								// output format specified below)
	{
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

}
