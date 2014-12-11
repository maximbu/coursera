import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private class RandomQueueIterator implements Iterator<Item> {

		private int i;
		private Item[] arr;

		public RandomQueueIterator() {
			arr =  (Item[]) new Object[N];
			System.arraycopy(a, 0, arr, 0, N);
			StdRandom.shuffle(arr);
			i = 0;
		}

		@Override
		public boolean hasNext() {
			return i < N;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return arr[i++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private int N;
	private Item[] a;

	public RandomizedQueue() // construct an empty randomized queue
	{
		a = (Item[]) new Object[2];
	}

	public boolean isEmpty() // is the queue empty?
	{
		return N == 0;
	}

	public int size() // return the number of items on the queue
	{
		return N;
	}

	public void enqueue(Item item) // add the item
	{
		if(item == null)
			throw new NullPointerException();
		if (N == a.length)
			resize(2 * a.length); // double size of array if necessary
		a[N++] = item; // add item
	}

	public Item dequeue() // delete and return a random item
	{
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		int index = randomizeIndex();
		Item item = a[index];
		if(index != N-1)
		{
			a[index] = a[N-1];	// move last entry to fill the gap
		}
		a[N-1] = null; // to avoid loitering
		N--;
		// shrink size of array if necessary
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);
		return item;
	}

	private int randomizeIndex() {
		return StdRandom.uniform(N);		
	}

	public Item sample() // return (but do not delete) a random item
	{
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		int index = randomizeIndex();
		return a[index];
	}

	public Iterator<Item> iterator() // return an independent iterator over
										// items in random order
	{
		return new RandomQueueIterator();
	}

	// resize the underlying array holding the elements
	private void resize(int capacity) {
		assert capacity >= N;
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}

	public static void main(String[] args) // unit testing
	{
		RandomizedQueue<String> s = new RandomizedQueue<String>();
		String item = StdIn.readString();
        while (!item.equals("-1")) {
            if (!item.equals("-")) s.enqueue(item);
            else if (!s.isEmpty()) StdOut.println(s.dequeue() + " ");
            item = StdIn.readString();
        }
        StdOut.println("(" + s.size() + " left in queue)");
        
        for (String st : s)
        {
        	StdOut.println(st);
        }
        StdIn.readChar();
	}

}
