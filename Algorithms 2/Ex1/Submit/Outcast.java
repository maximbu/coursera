public class Outcast {

	private WordNet wordnet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		int[] outcastCalc = new int[nouns.length];
		for (int i = 0; i < nouns.length; i++) {
			String noun = nouns[i];
			outcastCalc[i] = 0;
			for (int j = 0; j < nouns.length; j++)
				outcastCalc[i] += wordnet.distance(noun, nouns[j]);
		}
		int maximal = Integer.MIN_VALUE;
		int index = -1;
		for (int i = 0; i < nouns.length; i++)
			if (outcastCalc[i] > maximal) {
				maximal = outcastCalc[i];
				index = i;
			}
		return nouns[index];
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			String[] nouns = In.readStrings(args[t]);
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}

}
