public class SAP {

	private class VW implements Comparable<VW> {

		public int v;
		public int w;

		public VW(int v, int w) {
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(VW vw1) {
			if (v > vw1.v)
				return 1;
			if (v == vw1.v) {
				if (w > vw1.w)
					return 1;
				if (w == vw1.w)
					return 0;
			}
			return -1;
		}

	}

	private class SAPRetVal {
		public int minLength;
		public int commonAncestor;

		public SAPRetVal(int len, int anc) {
			minLength = len;
			commonAncestor = anc;
		}
	}

	private Digraph graph;
	private ST<VW, SAPRetVal> cache = new ST<VW, SAPRetVal>();

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		graph = G;
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		return findSAP(v, w).minLength;
	}

	private SAPRetVal findSAP(int v, int w) {
		throwOutOfBoundsExceptionIfNeeded(v);
		throwOutOfBoundsExceptionIfNeeded(w);
		SAPRetVal cached = cache.get(new VW(v, w));
		if (cached != null)
			return cached;
		Queue<Integer> vList = new Queue<Integer>();
		vList.enqueue(v);
		Queue<Integer> wList = new Queue<Integer>();
		wList.enqueue(w);
		SAPRetVal retval = performSearch(vList, wList);
		cache.put(new VW(v, w), retval);
		return retval;
	}

	private SAPRetVal performSearch(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfsHelperV = new BreadthFirstDirectedPaths(
				graph, v);
		BreadthFirstDirectedPaths bfsHelperW = new BreadthFirstDirectedPaths(
				graph, w);
		int minLength = Integer.MAX_VALUE;
		int commonAncestor = -1;
		for (int i = 0; i < graph.V(); i++) {
			int vToI = bfsHelperV.distTo(i);
			int wToI = bfsHelperW.distTo(i);
			if (vToI == Integer.MAX_VALUE || wToI == Integer.MAX_VALUE
					|| vToI + wToI > minLength) {
				continue;
			}
			minLength = vToI + wToI;
			commonAncestor = i;
		}
		if (minLength == Integer.MAX_VALUE)
			minLength = -1;
		SAPRetVal retval = new SAPRetVal(minLength, commonAncestor);
		return retval;
	}

	private void throwOutOfBoundsExceptionIfNeeded(int v) {
		if (v < 0 || v >= graph.V()) {
			throw new java.lang.IndexOutOfBoundsException();
		}
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		return findSAP(v, w).commonAncestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		throwExceptionIfNeeded(v, w);
		return performSearch(v, w).minLength;
	}

	private void throwExceptionIfNeeded(Iterable<Integer> v, Iterable<Integer> w) {
		for (int i : v) {
			throwOutOfBoundsExceptionIfNeeded(i);
		}
		for (int i : w) {
			throwOutOfBoundsExceptionIfNeeded(i);
		}
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		throwExceptionIfNeeded(v, w);
		return performSearch(v, w).commonAncestor;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		// In in = new In(
		// "D:\\Studies\\Algo2\\Ex1\\wordnet-testing\\wordnet\\digraph1.txt");
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}

	}

}
