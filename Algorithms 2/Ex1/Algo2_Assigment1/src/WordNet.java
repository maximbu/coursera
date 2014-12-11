import java.util.ArrayList;
import java.util.List;

public class WordNet {

	private class WordNetNode {
		private String fullName;
		private Bag<Integer> related;

		public WordNetNode(String allWords) {
			setFullName(allWords);
			setRelated(new Bag<Integer>());
		}

		public void addRelated(int relatedWord) {
			getRelated().add(relatedWord);
		}

		public void setFullName(String name) {
			this.fullName = name;
		}

		public String getFullName() {
			return fullName;
		}

		public Bag<Integer> getRelated() {
			return related;
		}

		public void setRelated(Bag<Integer> relatedIndexes) {
			related = relatedIndexes;
		}

	}

	private ST<Integer, WordNetNode> words = new ST<Integer, WordNetNode>();
	private ST<String, Queue<Integer>> stToIndex = new ST<String, Queue<Integer>>();
	private Digraph graph;
	private SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		readSynsets(synsets);
		graph = new Digraph(words.size());
		readHypernyms(hypernyms);
		sap = new SAP(graph);
		throwExceptionIfNeeded();
	}

	private void throwExceptionIfNeeded() {
		DirectedCycle dc = new DirectedCycle(graph);
		if (dc.hasCycle())
			throw new IllegalArgumentException();
		
		int roots = 0;
		for (int i = 0; i < graph.V(); i++) {
			if (!graph.adj(i).iterator().hasNext())
				roots++;
		}

		if (roots != 1) {
			throw new IllegalArgumentException();
		}
	}

	private void readHypernyms(String hypernymsFile) {
		List<String> lines = readFile(hypernymsFile);
		// parse the data in the file
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] parts = line.split(",");

			int index = Integer.parseInt(parts[0]);
			WordNetNode node = words.get(index);
			for (int j = 1; j < parts.length; j++) {
				int relatedIndex = Integer.parseInt(parts[j]);
				node.addRelated(relatedIndex);
				graph.addEdge(index, relatedIndex);
			}
		}
	}

	private void readSynsets(String synsetsFile) {
		List<String> lines = readFile(synsetsFile);
		// parse the data in the file

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] parts = line.split(",");

			int index = Integer.parseInt(parts[0]);
			String[] allWords = parts[1].split(" ");
			words.put(index, new WordNetNode(parts[1]));
			for (String word : allWords) {
				Queue<Integer> indexesQueue = stToIndex.get(word);
				if (indexesQueue == null) {
					indexesQueue = new Queue<>();
					stToIndex.put(word, indexesQueue);
				}
				indexesQueue.enqueue(index);
			}
		}
	}

	private List<String> readFile(String file) {
		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		In in = new In(file);
		String line = null;
		while ((line = in.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}

	// the set of nouns (no duplicates), returned as an Iterable
	public Iterable<String> nouns() {
		return stToIndex.keys();
	}

	private Iterable<Integer> getIndexesOfWord(String word) {
		return stToIndex.get(word);
	}

	private String indexToWord(int index) {
		return words.get(index).getFullName();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return stToIndex.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new java.lang.IllegalArgumentException();
		return sap.length(getIndexesOfWord(nounA), getIndexesOfWord(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new java.lang.IllegalArgumentException();
		int ancestor = sap.ancestor(getIndexesOfWord(nounA),
				getIndexesOfWord(nounB));
		return indexToWord(ancestor);
	}

	public static void main(String[] args) {
		WordNet w = new WordNet("D:\\Studies\\Algo2\\Ex1\\Data\\synsets.txt",
				"D:\\Studies\\Algo2\\Ex1\\Data\\hypernymsInvalidTwoRoots.txt");
		System.out.println(w.sap("tree_wallaby", "Rameses_the_Great"));
		// System.out.println(w.sap("1530s", "15_May_Organization"));
		// System.out
		// .println(w.sap("Rajab", "Battle_of_Ravenna"));
		// System.out.println(w.distance("Quezon_City",
		// "Punic_War"));
	}

}
