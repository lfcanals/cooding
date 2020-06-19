import java.util.*;
/**
 * write a function, `topTwo` that takes in a graph and a starting
 * node and returns the two lower costs to each node.
 * You can assume that the graph is connected.
 */
public class TopTwoShortestDistances {
  public static class Graph {
    public List<Link>[] links;

    public Graph(final int nodes) {
      links = new List[nodes];
      for(int i=0; i<nodes; i++) {
        links[i] = new ArrayList<Link>();
      }
    }

    public void addLink(final int from, final int to, final int w) {
      links[from].add(new Link(from, to, w));
    }


    public Collection<NodeWithDistances> topTwo(final int origin) {
      // Just do a variation of Dijkstra
      final Queue<NodeWithDistances> queue = new PriorityQueue<>();
      final Set<Integer> alreadyVisited = new HashSet<>();
      final Map<Integer, NodeWithDistances> visitedNeighbours = new HashMap<>();

      final NodeWithDistances firstNode = new NodeWithDistances(origin, 0,0);
      visitedNeighbours.put(origin, firstNode);
      queue.add(firstNode);
      while( ! queue.isEmpty() ) {
        NodeWithDistances node = queue.poll();
        alreadyVisited.add(node.node);
        for(final Link l : this.links[node.node]) {
          final int neighInt = l.to;
          NodeWithDistances n2 = visitedNeighbours.getOrDefault(neighInt,
                  new NodeWithDistances(neighInt));
          int newDistance = node.distance1 + l.weight;
          if(newDistance <= n2.distance1) {
            n2.distance2 = n2.distance1;
            n2.distance1 = newDistance;
          } else {
            if(newDistance < n2.distance2) {
              n2.distance2 = newDistance;
            }
          }
          visitedNeighbours.put(neighInt, n2);
          if(!alreadyVisited.contains(neighInt)) queue.add(n2);
        }
      }

      return visitedNeighbours.values();
    }
  }

  public static class Link {
    public int from, to, weight;
    public Link(final int from, final int to, final int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    public String toString() { return "(" +from+ "->" +to+ "=" +weight+ ")"; }
  }

  public static class NodeWithDistances 
      implements Comparable<NodeWithDistances>{
    public int node;
    public int distance1, distance2;
    public NodeWithDistances(int node) {
      this.node = node;
      distance1 = Integer.MAX_VALUE;
      distance2 = Integer.MAX_VALUE;
    }

    public NodeWithDistances(final int node, final int d1, final int d2) {
      this.node = node;
      distance1 = d1;
      distance2 = d2;
    }

    public int compareTo(NodeWithDistances n) {
      return this.distance1 - n.distance1;
    }

    public String toString() { 
      return "(" +node+ "," +distance1+ "/" +distance2+ ")"; 
    }
  }

  public static void main(final String args[]) {
    /*
     *     (0) ---3--> (1)
     *      |           |
     *      8           1
     *      |           |
     *      V           V
     *     (3) <--1--> (2)
     *      |           |
     *      6           |
     *      |           |
     *      V           |
     *     (4) <--4-----/
     */
    final Graph graph = new Graph(5);
    graph.addLink(0,1,3);
    graph.addLink(0,3,8);
    graph.addLink(1,2,1);
    graph.addLink(2,4,4);
    graph.addLink(2,3,1);
    graph.addLink(3,4,6);
    graph.addLink(3,2,1);

    System.out.println(graph.topTwo(0));
    System.out.println(graph.topTwo(2));
  }
}

