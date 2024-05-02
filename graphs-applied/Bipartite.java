import java.util.*;

/**
 * Detection (AND COLOURING !!) of a graph if it's bipartite, or not.
 *
 * Bipartite graph:
 *
 *      we can create TWO SETS: A, B
 *      such that
 *      every node a from A only connects with nodes b from B
 *      every node b from B only connects with nodes a from A
 *      no node from B connects to B, no node from A connects to A
 *
 *
 *  Idea of Prim: BFS or DFS go thorugh the graph colouring
 */
public class Bipartite {
  public static class Graph {
    public List<Integer>[] links;
    public Graph(final int numOfNodes) {
      links = new List[numOfNodes];
      for(int i=0; i<numOfNodes; i++) {
        links[i] = new ArrayList<Integer>();
      }
    }

    /**
     * Adds bideirectional link.
     */
    public void addLink(final int from, final int to) {
      links[from].add(to);
      links[to].add(from);
    }

    public String toString() {
      StringBuilder sb = new StringBuilder("[");
      for(int i=0; i<links.length; i++) {
        sb.append(links[i]).append(i==links.length-1 ? "\n" : ",\n");
      }
      sb.append("]");
      return sb.toString();
    }
  }


  public static boolean isBipartite(final Graph g) {
    final int RED = 1, BLUE = -1, CLEAR = 0;
    final int color[] = new int[g.links.length];

    final Deque<Integer> list = new LinkedList<>();
    list.add(0);
    color[0] = RED;
    while( ! list.isEmpty() ) {
      final int node = list.poll();
      for(int v : g.links[node]) {
        if(color[v] == CLEAR) {
          color[v] = -color[node];
          list.add(v);
        } else {
          if(color[v] == color[node]) return false;
        }
      }
    }
    return true;
  }


  public static void main(final String args[]) {
    Graph g = new Graph(4);

    g.addLink(0,1);
    g.addLink(0,3);
    g.addLink(1,2);
    g.addLink(2,3);

    System.out.println(g + (isBipartite(g) ? " is":" is not") + " bipartite");

    g.addLink(0,0);

    System.out.println(g + (isBipartite(g) ? " is":" is not") + " bipartite");
  }
} 
