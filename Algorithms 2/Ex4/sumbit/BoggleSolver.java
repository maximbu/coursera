public class BoggleSolver {
	private BoogleTrie2 trie;

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		trie = new BoogleTrie2();
		for (String st : dictionary) {
			trie.add(st);
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an
	// Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		BoogleTrie q = new BoogleTrie();
		int N = board.cols();
		int M = board.rows();
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				boolean[][] marked = new boolean[M][N];
				String startLetter = getBoardLetter(board, i, j);
				if(!shouldStopSearch(startLetter))
				{
					findWords(i, j, board, marked, startLetter, q);
				}
			}
		}
		return q.keys();
	}
	
	private String getBoardLetter(BoggleBoard board, int i, int j) {
		char letter = board.getLetter(i, j);
		if(letter != 'Q')
		{
			return String.valueOf(letter);
		}
		else return "QU";
	}

	private void findWords(int i, int j, BoggleBoard board, boolean[][] marked,
			String wordSoFar, BoogleTrie q) {
		marked[i][j] = true;
		for (Pair p : getNeighbors(i, j, board.rows(), board.cols())) {
			String currWord = wordSoFar;			
			if (!marked[p.i][p.j]) {
				currWord += getBoardLetter(board, p.i, p.j);
				if (currWord.length() > 2 && isInDictionary(currWord)) {
					q.put(currWord);
				}
				if(!shouldStopSearch(currWord))
				{
					findWords(p.i, p.j, board, marked, currWord, q);
				}
			}
		}
		marked[i][j] = false;
	}

	private boolean shouldStopSearch(String wordSoFar) {
		return !trie.hasPrefixOf(wordSoFar);
	}

	private Iterable<Pair> getNeighbors(int i, int j, int M, int N) {
		Queue<Pair> q = new Queue<Pair>();
		addIfValid(i - 1, j, M, N, q);
		addIfValid(i + 1, j, M, N, q);
		addIfValid(i, j - 1, M, N, q);
		addIfValid(i, j + 1, M, N, q);
		addIfValid(i - 1, j - 1, M, N, q);
		addIfValid(i + 1, j + 1, M, N, q);
		addIfValid(i + 1, j - 1, M, N, q);
		addIfValid(i - 1, j + 1, M, N, q);
		return q;
	}

	private void addIfValid(int i, int j, int M, int N, Queue<Pair> q) {
		if (isValidCell(i, j, M, N))
			q.enqueue(new Pair(i, j));
	}

	private boolean isValidCell(int i, int j, int M, int N) {
		if (i < 0 || j < 0 || i >= M || j >= N)
			return false;
		return true;
	}

	private int toIntRep(int i, int j, int N) {
		return N * i + j;
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through
	// Z.)
	public int scoreOf(String word) {
		if (!isInDictionary(word))
			return 0;
		int len = word.length();
		switch (len) {
		case 0:
		case 1:
		case 2:
			return 0;
		case 3:
		case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 5;
		case 8:
		default:
			return 11;
		}

	}

	private boolean isInDictionary(String word) {
		return trie.contains(word);
	}

	public static void main(String[] args) {
		String path = "D:\\Studies\\Algo2\\Ex4\\Algo2_Assigment4\\boggle\\";
		//String dictFile = path + "dictionary-algs4.txt";
		//String boardFile = path + "board4x4.txt";
		String dictFile = path + "dictionary-yawl.txt";
		String boardFile = path + "board-antidisestablishmentarianisms.txt";
		//String dictFile = args[0];
		//String boardFile = args[1];

		In in = new In(dictFile);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(boardFile);
		int score = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}
}