import java.util.*;

/**
 * For a graph calculates EULERIAN CIRCUIT: closed + unique visit per edge.
 *
 * Assuming it's possible, that's iif #vertex_with_odd_edges = 0
 *
 */
public class Hierholzer {
  /**
   * Unidirected == non-directed graph.
   */
  public static class Graph {
    public List<Integer>[] links;
    public Graph(final int numOfNodes) {
      this.links = new List[numOfNodes];
      for(int i=0; i<numOfNodes; i++) this.links[i] = new ArrayList<Integer>();
    }

    public void addLink(final int from, final int to) {
      this.links[from].add(to);
      this.links[to].add(from);
    }
  }

  public static class VisitedLink {
    public int from, to;
    public VisitedLink(final int from, final int to) {
      this.from = from; this.to = to;
    }
    @Override public int hashCode() { return this.from * this.to; }
    @Override public boolean equals(final Object o) { 
      final VisitedLink ov = (VisitedLink) o;
      return (this.from == ov.from && this.to == ov.to)
            || (this.to == ov.from && this.from == ov.to);
    }
  }

  public static List<Integer> eulerianCircuit(final Graph g) {
    final Map<Integer/*vertex*/, Integer/*degree*/> edgesFromVertex
        = new HashMap<>();
    for(int i=0; i<g.links.length; i++) {
      if(g.links[i].size() % 2 != 0) return null;
      edgesFromVertex.put(i, g.links[i].size());
    }

    final Set<VisitedLink> visitedLinks = new HashSet<>();

    final Deque<Integer> stack = new ArrayDeque<>();

    final List<Integer> path = new ArrayList<>();

    int node = 0;
    do {
      if(edgesFromVertex.get(node) > 0) {
        stack.push(node);
        for(int i=0; i<g.links[node].size(); i++) {
          final VisitedLink e = new VisitedLink(node, g.links[node].get(i));
          if(!visitedLinks.contains(e)) {
            // Marks used the link from node -> nextNode
            edgesFromVertex.put(node, edgesFromVertex.get(node)-1);
            visitedLinks.add(e);
            node = g.links[node].get(i);
            // Marks used the link from nextNode -> node
            edgesFromVertex.put(node, edgesFromVertex.get(node)-1);
            break;
          }
        }
      } else {
        path.add(node);
        node = stack.pop();
      }
    } while(!stack.isEmpty());

    path.add(0);
    Collections.reverse(path);
    return path;
  }


  public static void main(final String args[]) {
    final Graph g = new Graph(6);
    g.addLink(1,2);
    g.addLink(2,3);
    g.addLink(3,4);
    g.addLink(3,5);
    g.addLink(4,0);
    g.addLink(5,0);
    g.addLink(0,1);
    g.addLink(0,3);

    /*
     *   1 ------- 2
     *   |         |
     *   |         |
     *   0 -- 4 -- 3
     *   \---------/
     *   |         |
     *   +--- 5 ---+
     */
    System.out.println(eulerianCircuit(g));
  }
}
