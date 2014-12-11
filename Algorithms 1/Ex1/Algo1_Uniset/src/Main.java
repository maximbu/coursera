
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//hw1Helper();
		ex1Helper();
	}

	private static void ex1Helper() {
		Percolation p = new Percolation(2);
		System.out.println("p.percolates() = " + p.percolates());
		System.out.println(p.isFull(1, 1));
		System.out.println(p.isOpen(1, 1));
		p.open(1, 1);
		p.open(1, 2);
		System.out.println("p.percolates() = " + p.percolates());
		p.open(2, 2);
		System.out.println("p.percolates() = " + p.percolates());
		p.open(2, 3);
		System.out.println("p.percolates() = " + p.percolates());
		p.open(3, 3);
		System.out.println("p.percolates() = " + p.percolates());
	}

	private static void hw1Helper() {
		WeightedQuickUnionUF q = new WeightedQuickUnionUF(10);
		Test5(q);
		q.print();
	}
	
	private static void Test1(QuickFindUF q)
	{
		q.union(9, 0);
		q.union(5, 1);
		q.union(9, 8);
		q.union(4, 5);
		q.union(6, 5);
		q.union(2, 4);
	}
	
	private static void Test2(QuickFindUF q)
	{
		q.union(9, 2);
		q.union(0, 6);
		q.union(6, 9);
		q.union(4, 2);
		q.union(0, 1);
		q.union(8, 1);
	}
	
	private static void Test3(WeightedQuickUnionUF q)
	{
		q.union(0, 2);
		q.union(9, 4);
		q.union(1, 8);
		q.union(8, 2);
		q.union(6, 9);
		q.union(9, 5);
		q.union(4, 8);
		q.union(7, 2);
		q.union(9, 3);
	}
	
	private static void Test4(WeightedQuickUnionUF q)
	{
		q.union(1,9);
		q.union(0,7);
		q.union(5,6);
		q.union(2,8);
		q.union(6,1);
		q.union(3,2);
		q.union(8,4);
		q.union(3,9);
		q.union(2,7);
	}
	
	private static void Test5(WeightedQuickUnionUF q)
	{
		q.union(7,0);
		q.union(0,9);
		q.union(5,2);
		q.union(6,8);
		q.union(0,4);
		q.union(5,6);
		q.union(0,2);
		q.union(3,5);
		q.union(0,1);
	}

}
