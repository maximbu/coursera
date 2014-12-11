public class Percolation {

	private WeightedQuickUnionUF percUF;
	private WeightedQuickUnionUF backWashUF;
	private int virtualStart;
	private int virtualEnd;
	private int origN;
	private boolean[][] isEmpty;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		origN = N;
		percUF = new WeightedQuickUnionUF(N * N + 2);
		backWashUF = new WeightedQuickUnionUF(N * N + 1);
		virtualStart = 0;
		virtualEnd = N * N + 1;
		for (int j = 1; j < N + 1; j++) {
			percUF.union(virtualStart, trasnformToUnionCoord(1, j));
			backWashUF.union(virtualStart, trasnformToUnionCoord(1, j));
			percUF.union(virtualEnd, trasnformToUnionCoord(N, j));
		}

		isEmpty = new boolean[N + 1][N + 1];
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < N + 1; j++) {
				isEmpty[i][j] = false;
			}
		}
	}

	public void open(int i, int j) // open site (row i, column j) if it is not
									// already
	{
		if (isOutOfBounds(i, j, true))
			return;
		if (isEmpty[i][j]) {
			return;
		}

		connectIfOpen(i, j, i, j + 1);
		connectIfOpen(i, j, i, j - 1);
		connectIfOpen(i, j, i + 1, j);
		connectIfOpen(i, j, i - 1, j);

		isEmpty[i][j] = true;
	}

	private boolean isOutOfBounds(int i, int j, boolean throwException) {
		if (i < 1 || i > origN || j < 1 || j > origN) {
			if (throwException) {
				throw new java.lang.IndexOutOfBoundsException();
			}
			return true;
		}
		return false;
	}

	private void connectIfOpen(int i, int j, int new_i, int new_j) {
		if (!isOutOfBounds(new_i, new_j, false) && isOpen(new_i, new_j)) {
			percUF.union(trasnformToUnionCoord(i, j),
					trasnformToUnionCoord(new_i, new_j));
			backWashUF.union(trasnformToUnionCoord(i, j),
					trasnformToUnionCoord(new_i, new_j));
		}
	}

	private int trasnformToUnionCoord(int i, int j) {
		return (i - 1) * origN + j;
	}

	public boolean isOpen(int i, int j) // is site (row i, column j) open?
	{
		if (isOutOfBounds(i, j, true))
			return false;
		return isEmpty[i][j];
	}

	public boolean isFull(int i, int j) // is site (row i, column j) full?
	{
		return isOpen(i, j)
				&& backWashUF.connected(trasnformToUnionCoord(i,j),virtualStart);
	}

	public boolean percolates() // does the system percolate?
	{
		return percUF.connected(virtualStart, virtualEnd);
	}

}
