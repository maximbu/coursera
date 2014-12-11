public class BurrowsWheeler {
	// private static final String IN =
	// "D:\\Studies\\Algo2\\Ex5\\burrows-testing\\burrows\\aesop.txt";
	// private static final String OUT =
	// "D:\\Studies\\Algo2\\Ex5\\burrows-testing\\burrows\\out";
	private final static String EncodeSt = "-";
	private final static String DecodeSt = "+";

	// apply Burrows-Wheeler encoding, reading from standard input and writing
	// to standard output
	public static void encode() {
		String st = BinaryStdIn.readString();
		// String st = new BinaryIn(IN).readString();
		// BinaryOut out = new BinaryOut(OUT);
		CircularSuffixArray cx = new CircularSuffixArray(st);

		int N = cx.length();
		int start = printStart(cx, N);
		// out.write(start);
		BinaryStdOut.write(start);
		for (int i = 0; i < N; i++) {
			char ch = st.charAt((N - 1 + cx.index(i)) % N);
			BinaryStdOut.write(ch);
			// out.write(ch);
		}
		BinaryStdOut.close();
		// out.close();
	}

	private static int printStart(CircularSuffixArray cx, int N) {
		for (int i = 0; i < N; i++) {
			if (cx.index(i) == 0) {
				return i;
			}
		}
		return -1;
	}

	// apply Burrows-Wheeler decoding, reading from standard input and writing
	// to standard output
	public static void decode() {

		// BinaryIn in = new BinaryIn(OUT);
		// int start = in.readInt();
		// char[] t = in.readString().toCharArray();
		int start = BinaryStdIn.readInt();
		char[] t = BinaryStdIn.readString().toCharArray();

		int[] next = getNext(t);

		int charInd = start;
		for (int i = 0; i < t.length; i++) {
			charInd = next[charInd];
			// BinaryStdOut.write(sorted[charInd]);
			BinaryStdOut.write(t[charInd]);
		}
		BinaryStdOut.close();
	}

	private static int[] getNext(char[] t) {
		int[] next = new int[t.length];
		int R = 256;
		int[] sums = new int[R + 1];
		for (int k = 0; k < t.length; k++) {
			sums[t[k] + 1]++;
		}
		for (int k = 1; k < sums.length; k++) {
			sums[k] += sums[k - 1];
		}
		for (int k = 0; k < t.length; k++) {
			int index = sums[t[k]]++;
			next[index] = k;
		}
		return next;
	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {

		if (args[0] == EncodeSt) {
			encode();
		}

		if (args[0] == DecodeSt) {
			decode();
		}
	}
}