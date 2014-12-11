import java.util.Arrays;

public class CircularSuffixArray2 {

	private Suffix[] suffixes;

	public CircularSuffixArray2(String s) // circular suffix array of s
	{
		int N = s.length();
		this.suffixes = new Suffix[N];
		for (int i = 0; i < N; i++)
			suffixes[i] = new Suffix(s, i);
		Arrays.sort(suffixes);
	}

	private static class Suffix implements Comparable<Suffix> {
		private final String text;
		private final int index;
		private final int len;

		private Suffix(String text, int index) {
			this.text = text;
			this.index = index;
			this.len = text.length();
		}

		private char charAt(int i) {
			return text.charAt((index + i) % len);
		}

		public int compareTo(Suffix that) {
			if (this == that)
				return 0; // optimization
			for (int i = 0; i < len; i++) {
				if (this.charAt(i) < that.charAt(i))
					return -1;
				if (this.charAt(i) > that.charAt(i))
					return +1;
			}
			return 0;
		}

		public String toString() {
			return text.substring(index);
		}
	}

	/**
	 * Returns the length of the input string.
	 * 
	 * @return the length of the input string
	 */
	public int length() {
		return suffixes.length;
	}

	/**
	 * Returns the index into the original string of the <em>i</em>th smallest
	 * suffix. That is, <tt>text.substring(sa.index(i))</tt> is the <em>i</em>th
	 * smallest suffix.
	 * 
	 * @param i
	 *            an integer between 0 and <em>N</em>-1
	 * @return the index into the original string of the <em>i</em>th smallest
	 *         suffix
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless 0 &le; <em>i</em> &lt; <Em>N</em>
	 */
	public int index(int i) {
		if (i < 0 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return suffixes[i].index;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CircularSuffixArray2 a = new CircularSuffixArray2("ABRACADABRA!");
		for (int i = 0; i < a.length(); i++) {
			StdOut.println(a.index(i));
		}

	}
}
