import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author max
 * 
 */
public class Deque<Item> implements Iterable<Item> {

	private class DQueueIterator<T> implements Iterator<Item> {
		private Node<Item> current;

		public DQueueIterator(Node<Item> first) {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	private int N; // size of the d-queue
	private Node<Item> first; // top of queue
	private Node<Item> last; // top of queue

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
	}

	public Deque() // construct an empty deque
	{
		first = null;
		last = null;
		N = 0;
	}

	public boolean isEmpty() // is the deque empty?
	{
		return N == 0;
	}

	public int size() // return the number of items on the deque
	{
		return N;
	}

	public void addFirst(Item item) // insert the item at the front
	{
		if (item == null) {
			throw new NullPointerException();
		}
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		first.prev = null;
		if (oldfirst != null) {
			oldfirst.prev = first;
		}
		N++;
		if (last == null) {
			last = first;
		}
	}

	public void addLast(Item item) // insert the item at the end
	{
		if (item == null) {
			throw new NullPointerException();
		}
		Node<Item> oldlast = last;
		last = new Node<Item>();
		last.item = item;
		last.next = null;
		last.prev = oldlast;
		if (oldlast != null) {
			oldlast.next = last;
		}
		N++;
		if (first == null) {
			first = last;
		}
	}

	public Item removeFirst() // delete and return the item at the front{
	{
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = first.item; // save item to return
		first = first.next; // delete first node
		if (first != null) {
			first.prev = null;
		}
		N--;
		if (N <= 1) {
			last = first;
		}
		return item; // return the saved item
	}

	public Item removeLast() // delete and return the item at the end
	{
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = last.item; // save item to return
		last = last.prev; // delete first node
		if (last != null) {
			last.next = null;
		}
		N--;
		if (N <= 1) {
			first = last;
		}
		return item; // return the saved item
	}

	public Iterator<Item> iterator() // return an iterator over items in order
										// from front to end
	{
		return new DQueueIterator<Item>(first);
	}

	public static void main(String[] args) // unit testing
	{
		Deque<String> d = new Deque<String>();
		d.addFirst("a");
		d.addFirst("b");
		d.addFirst("c");
		StdOut.println(d.removeLast());
		StdOut.println(d.removeLast());
		StdOut.println(d.removeLast());
		d.addFirst("d");
		d.addLast("e");
		StdOut.println(d.removeLast());
		StdOut.println(d.removeFirst());
	}
}
