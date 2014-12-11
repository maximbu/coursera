public class WeightedQuickUnionUF {
	private int[] id;
	private int[] sz;
	private int size;

	public WeightedQuickUnionUF(int N) {
		size = N;
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < size; i++)
		{
			id[i] = i;
			sz[i]=1;
		}
	}

	private int root(int i) {
		while (i != id[i])
			i = id[i];
		return i;
	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		if (i == j)
			return;
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
	}

	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.print(id[i] + " ");
		}
		System.out.println();
	}
}