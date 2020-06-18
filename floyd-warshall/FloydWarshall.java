
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates distances from every node to the rest of nodes.
 */
public class FloydWarshall {
  /*
   * Algorithm:
   *
   *    Let be nodes= [0,1,2,...N-1]
   *    And initial distances: D(0)[i,j] = cost(from i to j), i,j=0,..N-1
   *                                       0 if i==j, INF if no direct edge
   *
   *    for k=0,1,2,...,N-1
   *      for i,j = 0,1,....,N-1
   *        D(k+1)[i,j] = min( D(k)[i,j],  D(k)[i,k]+D(k)[k,j] )
   *
   *    print D(N)
   *
   *
   *
   *    O(V^3)
   *
   *    Versus Dijkstra/Astar over all nodes: V^2 · O(ElogV) = O(V^2 ElogV)
   *                                                worst case O(V^3 logV)
   */


  public static class Link {
    public int from, to;
    public float cost;
    public Link(int from, int to, float cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  public double[][] floydWarshall(final List<Link> links) {
    int maxN = 0;
    for(final Link link : links) {
      if(link.from > maxN) maxN=link.from;
      if(link.to > maxN) maxN=link.to;
    }

    final int N = maxN+1;
    final double[][] dist = new double[N][N];
    for(int i=0; i<N; i++) {
      for(int j=0; j<N; j++) {
        if(i==j) dist[i][j] = 0;
        else dist[i][j] = Double.POSITIVE_INFINITY;
      }
    }

    for(final Link l : links) dist[l.from][l.to] = l.cost;


    for(int k=0; k<N; k++) {
      for(int i=0; i<N; i++) {
        for(int j=0; j<N; j++) {
          dist[i][j] = Math.min(dist[i][k] + dist[k][j], dist[i][j]);
        }
      }
    }

    return dist;
  }



  public static void main(final String args[]) {
    /* Let us create the following weighted graph
           10
        (0)------->(3)
        |         /|\
        5 |          |
        |          | 1
        \|/         |
        (1)------->(2)
           3           
    */
    final List<Link> graph = new ArrayList<>();
    graph.add(new Link(0,1,5));
    graph.add(new Link(1,2,3));
    graph.add(new Link(2,3,1));
    graph.add(new Link(0,3,10));

    final double[][] dist = new FloydWarshall().floydWarshall(graph);

    for(int i=0; i<dist.length; i++) {
      for(int j=0; j<dist[i].length; j++) {
        System.out.println("From " + i + " to " + j + "=" + dist[i][j]);
      }
    }
  }
}
