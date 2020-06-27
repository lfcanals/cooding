import java.util.*;

/**
 * Ford Flukenson algorithm to calculate maximum flow in a graph.
 *
 * The idea:
 *    clone the graph to "residualGraph"
 *
 *    maxFlow = 0;
 *    while( (path = residualGraph.=bfsPath(from, to)) != null ) {
 *      maxResidualFlow = minimumFlow(path);
 *      residualGraph.removeFlow( path, maxResidualFlow );
 *      maxFlow += maxResidualFlow;
 *    }
 */
public class FordFlukerson {
  public static class Graph {
    public int[][] capacity;
    public Graph(int nodes) {
      this.capacity = new int[nodes][];
      for(int i=0; i<nodes;i++) this.capacity[i] = new int[nodes];
    }

    public void addLink(int a,int b, int cap) {
      this.capacity[a][b] = cap;
    }

    @Override
    public Object clone() {
      final Graph g = new Graph(this.capacity.length);
      for(int i=0; i<this.capacity.length; i++) {
        for(int j=0; j<this.capacity.length; j++) {
          g.capacity[i][j] = this.capacity[i][j];
        }
      }
      return g;
    }


    /**
     * Return a path from "from" to "to" as an array of ints.
     * @return array of ints in which position-i is the parent node in path.
     */
    public int[] bfs(final int from, final int to) {
      final LinkedList<Integer> fifo = new LinkedList<>();
      fifo.add(from);
      final boolean visited[] = new boolean[this.capacity.length];
      visited[from] = true;
      final int[] path = new int[this.capacity.length];
      while(!fifo.isEmpty()) {
        final int node = fifo.removeFirst();
        for(int n=0; n<this.capacity[node].length; n++) {
          if(this.capacity[node][n] > 0 && !visited[n]) {
            path[n] = node;
            visited[n] = true;
            if(n==to) return path;
            fifo.addLast(n);
          }
        }
      }
      return null;
    }
  }


  /**
   * Calculates maximum flow in graph from a to b.
   */
  public static int fordFlukerson(final Graph g, int from, int to) {
    int maxFlow = 0;
    final Graph residualG = (Graph) g.clone();

    for(int[] path = residualG.bfs(from, to); path!=null; 
            path=residualG.bfs(from, to)) {
      // Calculates maximum flow in residual graph in path a->b
      int node = to;
      int maxResidualFlow = Integer.MAX_VALUE;
      while(node!=from) {
        final int parent = path[node];
        if(residualG.capacity[parent][node]<maxResidualFlow) {
          maxResidualFlow = residualG.capacity[parent][node];
        }
        node = parent;
      }

      // Decreases flow in residual graph in path a->b
      node = to;
      while(node!=from) {
        final int parent = path[node];
        residualG.capacity[parent][node] -= maxResidualFlow;
        node = parent;
      }

      // Increases maximum flow
      maxFlow += maxResidualFlow;
    }

    return maxFlow;
  }


  public static void main(final String args[]) {
    final Graph g = new Graph(6);
    g.addLink(0,1,11);
    g.addLink(1,3,12);
    g.addLink(3,5,19);
    g.addLink(0,2,12);
    g.addLink(2,4,11);
    g.addLink(4,5,4);
    g.addLink(2,1,1);
    g.addLink(4,3,7);

    System.out.println(fordFlukerson(g, 0,5));
  }
}
