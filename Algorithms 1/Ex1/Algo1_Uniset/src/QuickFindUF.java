public class QuickFindUF {
	private int[] id;
	private int size;

	public QuickFindUF(int N) {
		id = new int[N];
		size = N;
		for (int i = 0; i < size; i++)
			id[i] = i;
	}

	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++)
			if (id[i] == pid)
				id[i] = qid;
	}
	
	public void print()
	{
		for (int i = 0; i < size; i++)
		{
			System.out.print(id[i]+ " ");
		}
		System.out.println();
	}
}
