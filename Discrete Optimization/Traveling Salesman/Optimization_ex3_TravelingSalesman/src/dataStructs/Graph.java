package dataStructs;

public class Graph {
    private final int V;
    private int E;
    private int[] next;
    
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        next = new int[V];
    }


    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the graph.
     * @return the number of edges in the graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge v-w to the graph.
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        E++;
        next[v]= w;
    }


    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     * @return the vertices adjacent to vertex <tt>v</tt> as an Iterable
     * @param v the vertex
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public int next(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return next[v];
    }


}
