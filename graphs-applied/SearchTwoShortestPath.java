/**
 * write a function, `topTwo` that takes in a graph and a starting
 * node and returns two paths, the first and second shortest paths,
 * for all the other nodes in the graph.  You can assume that the
 * graph is connected.
 */
public class SearchTwoShortestPath {
  public static class Graph {
    public List<Link>[] links;

    public void Graph(final int nodes) {
      links = new List[nodes];
      for(int i=0; i<nodes; i++) {
        links = new ArrayList<Link>();
      }
    }

    public void addLink(final int from, final int to, final int w) {
      links[from].add(new Link(from, to, w));
    }


    public Collection<NodeWithDistances> topTwo(final int origin) {
      // Just do a variation of Dijkstra
      final Queue<NodeWithDistances> openSet = new PriorityQueue<>();
      final Set<Integer> alreadyVisited = new HastSet<>();
      final Map<Integer, NodeWithDistances> visitedNeighbours = new HashSet<>();

      queue.add(new NodeWithDistances(origin, 0,0));
      while( ! queue.isEmpty() ) {
        NodeWithDistances node = queue.removeLast();
        alreadyVisited.add(node.node);
        for(Links l : this.neighbours(node.node)) {
          final int n = l.to;
          NodeWithDistances n2 = visitedNeighours.getOrDefault(n,
                  new NodeWithDistances(n));
          if(n.distance1 + l.weight < n2.distance1) {
            n2.distance2 = n2.distance1;
            n2.distance1 = n.distance1 + l.weight;
          }
          if(!alreadyVisited.contains(n)) queue.add(n2);
        }
      }

      return visitedNeighbours.values();
    }
  }

  public static class Link {
    public int from, to, weight;
    public Link(int from, int to, int weight) {
      from = from;
      to = to;
      weight = weight;
    }
  }

  public static class NodeWithDistances 
      implements Comparator<NodeWithDistances>{
    public int node;
    public int distance1, distance2;
    public NodeWithDistances(int node) {
      this.node = node;
      distance1 = Integer.MAX_VALUE;
      distance2 = Integer.MAX_VALUE;
    }

    public NodeWithDistances(int node, int d1, int d2) {
      this.node = node;
      distance1 = d1;
      distance2 = d2;
    }

    public int compareTo(NodeWithDistances n) {
      return this.distance1 < n.distance1;
    }
  }

  public static void main(final String args[]) {
    final Graph graph = new Graph(5);
    grap.addLink(
  }
}

