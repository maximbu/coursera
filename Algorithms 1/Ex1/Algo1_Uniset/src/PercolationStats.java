public class PercolationStats {
	private double[] stats;
	private int numOfExp;

	public PercolationStats(final int N, final int T) // perform T independent computational
											// experiments on an N-by-N grid
	{
		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		numOfExp = T;
		stats = new double[numOfExp];

		for (int i = 0; i < numOfExp; i++) {
			stats[i] = runExpirement(N);
		}
	}

	private double runExpirement(final int n) {
		Percolation p = new Percolation(n);
		int cnt = 0;
		while (!p.percolates()) {
			int i = chooseRandom(n);
			int j = chooseRandom(n);
			if (!p.isOpen(i, j)) {
				p.open(i, j);
				cnt++;
			}
		}
		return (double) cnt / (n * n);

	}

	private int chooseRandom(final int n) {
		return StdRandom.uniform(n) + 1;
	}

	public double mean() // sample mean of percolation threshold
	{
		return StdStats.mean(stats);
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(stats);
	}

	public double confidenceLo() // returns lower bound of the 95% confidence
									// interval
	{
		double miu = mean();
		double delta = stddev();
		double val = 1.96 * delta / Math.sqrt(numOfExp);
		return miu - val;
	}

	public double confidenceHi() // returns upper bound of the 95% confidence
									// interval
	{
		double miu = mean();
		double delta = stddev();
		double val = 1.96 * delta / Math.sqrt(numOfExp);
		return miu + val;
	}

	public static void main(final String[] args) // test client, described below
	{
		int n;
		int t;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
			t = Integer.parseInt(args[0]);
		} else {
			n = 20;
			t = 1000;
		}
		PercolationStats p = new PercolationStats(n, t);
		System.out.println("mean                     = " + p.mean());
		System.out.println("stddev                   = " + p.stddev());
		System.out.println("95% confidence interval = " + p.confidenceLo()
				+ ", " + p.confidenceHi());
	}
}