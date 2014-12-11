public class MoveToFront {
	private final static String EncodeSt = "-";
	private final static String DecodeSt = "+";

	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		int[] indexes = initIndexes();
		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			printAndUpdate(indexes, c);
		}
		BinaryStdOut.close();
	}

	private static void printAndUpdate(int[] indexes, char c) {
		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] == c) {
				BinaryStdOut.write((char) i);
				for (int k = i; k >= 1; k--) {
					indexes[k] = indexes[k - 1];
				}
				indexes[0] = c;
				break;
			}
		}
	}

	// private static void encodeInternal() {
	// LinkedList<Character> list = initList();
	//
	// while (!BinaryStdIn.isEmpty()) {
	// char c = BinaryStdIn.readChar();
	// int index = list.indexOf(c);
	// BinaryStdOut.write((char) index);
	// list.addFirst(list.remove(index));
	// }
	//
	// BinaryStdOut.close();
	// }

	private static int[] initIndexes() {
		int[] indexes = new int[256];
		for (int k = 0; k < indexes.length; k++) {
			indexes[k] = k;
		}
		return indexes;
	}

	// private static LinkedList<Character> initList() {
	// LinkedList<Character> list = new LinkedList<Character>();
	// for (int i = 256; i >= 0; i--) {
	// list.addFirst((char) i);
	// }
	// return list;
	// }

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode() {
		int[] indexes = initIndexes();
		while (!BinaryStdIn.isEmpty()) {
			int index = BinaryStdIn.readChar();
			decodeIndex(indexes, index);
		}
		BinaryStdOut.close();

	}

	private static void decodeIndex(int[] indexes, int index) {
		int c = indexes[index];
		BinaryStdOut.write((char) c);
		for (int j = index; j > 0; j--) {
			indexes[j] = indexes[j - 1];
		}
		indexes[0] = c;
	}

	// private static void decodeInternal() {
	// LinkedList<Character> list = initList();
	//
	// while (!BinaryStdIn.isEmpty()) {
	// int index = BinaryStdIn.readChar();
	// char c = list.get(index);
	// BinaryStdOut.write(c);
	// list.addFirst(list.remove(index));
	// }
	//
	// BinaryStdOut.close();
	// }

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		//encode();
		// decode();
		if (args[0] == EncodeSt) {
			encode();
		}

		if (args[0] == DecodeSt) {
			decode();
		}
	}

}
