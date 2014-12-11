public class CircularSuffixArray {
	private static final int R = 256, CUTOFF = 15;
	private final int n;
	private int[] order;

	/**
	 * Circular suffix array of string <code>s</code>.
	 * <p>
	 * Running time is O(n^2 log n) in the worst case when is <code>n ==
	 * s.length()</code>. This occurs when there are long repeated substrings.
	 * In the average case the running time is O(n log n).
	 */
	public CircularSuffixArray(String s) {
		n = s.length();
		order = new int[n];
		for (int i = 0; i < n; i++)
			order[i] = i;
		sort(s, 0, n - 1, 0);
	}

	/**
	 * Length of the string (which is the number of suffixes).
	 * <p>
	 * If looping over the contets of the <code>index</code> method,
	 * <code>length()</code> is the appropriate upper bound for the loop.
	 * <p>
	 * Running time is constant.
	 */
	public int length() {
		return n;
	}

	/**
	 * Return index in the sorted order of the suffix beginning at index
	 * <code>i</code>.
	 * <p>
	 * If looping over the contets of the <code>index</code> method,
	 * <code>length()</code> is the appropriate upper bound for the loop.
	 * <p>
	 * Running time is constant.
	 */
	public int index(int i) {
		return order[i];
	}

	// Return the (offset)th character of the suffix beginning in s at index
	// suffix.
	private char charAt(String s, int suffix, int offset) {
		return s.charAt((suffix + offset) % n);
	}

	// 3-way String Quicksort circular suffixes of string s from lo to hi
	// starting at index offset. Code adapted from
	// http://algs4.cs.princeton.edu/51radix/Quick3string.java.html
	private void sort(String s, int lo, int hi, int offset) {
		if (hi - lo <= CUTOFF) {
			insertion(s, lo, hi, offset);
			return;
		}
		int lt = lo, gt = hi, piv = charAt(s, order[lo], offset), eq = lo + 1;
		while (eq <= gt) {
			int t = charAt(s, order[eq], offset);
			if (t < piv)
				exch(lt++, eq++);
			else if (t > piv)
				exch(eq, gt--);
			else
				eq++;
		}
		sort(s, lo, lt - 1, offset);
		if (piv >= 0)
			sort(s, lt, gt, offset + 1);
		sort(s, gt + 1, hi, offset);
	}

	private void exch(int i, int j) {
		int tmp = order[i];
		order[i] = order[j];
		order[j] = tmp;
	}

	// Insertion sort starting at index offset. Code adapted from
	// http://algs4.cs.princeton.edu/51radix/Quick3string.java.html
	private void insertion(String s, int lo, int hi, int offset) {
		for (int i = lo; i <= hi; i++)
			for (int j = i; j > lo && less(s, j, j - 1, offset); j--)
				exch(j, j - 1);
	}

	// Is suffix i less than suffix j, starting at offset
	private boolean less(String s, int i, int j, int offset) {
		int oi = order[i], oj = order[j];
		for (; offset < n; offset++) {
			int ival = charAt(s, oi, offset), jval = charAt(s, oj, offset);
			if (ival < jval)
				return true;
			else if (ival > jval)
				return false;
		}
		return false;
	}
}
