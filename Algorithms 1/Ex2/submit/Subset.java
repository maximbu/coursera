public class Subset {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		RandomizedQueue<String> s = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			s.enqueue(item);			
		}
		for (int i = 0; i < k; i++) {
			StdOut.println(s.dequeue());
		}
	}

}
