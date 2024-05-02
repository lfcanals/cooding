import java.util.*;


/**
 * Prim calculates de Minumun Tree that covers all nodes in a graph (MST).
 * O( E log V )
 *    O(log V) because of the priority queue resort
 *    O(E+V) = O(E) for each of the edges we need to evaluate
 *
 *
 * The idea is:
 *      tree = new Tree();
 *      tree.add(one random node of the Graph):
 *      Repeat until all nodes in tre:
 *          take the node in graph such that:
 *              node not still in tree
 *              direct distance to node is min from rest of nodes in tree
 *          tree.add(node)
 *  
 *      
 */
public class Prim {
  public static class Link {
    public int from,to,weight;
    public Link(final int from, final int to, final int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
    public String toString() {
      return "(" + from + "->" + to + "/" + weight + ")";
    }
  }

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

    public void addLink(final Link l) {
      if(l==null) return;
      links[l.from].add(l);
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
  

  public static class WeightedNode implements Comparable<WeightedNode>{
    public int node;
    public int weight;
    public Link linkFromPrevious;

    public WeightedNode(final int node) {
      this.node = node;
      this.linkFromPrevious = null;
      this.weight = Integer.MAX_VALUE;
    }

    public WeightedNode(final int node, final int weight) {
      this.node = node;
      this.linkFromPrevious = null;
      this.weight = weight;
    }
    public int compareTo(final WeightedNode other) {
      return this.weight - other.weight;
    }
  }



  public static Graph primMST(final Graph graph) {
    final Graph mstGraph = new Graph(graph.links.length);
    final Set<Integer> visited = new HashSet<>();
    final Map<Integer, WeightedNode> aux = new HashMap<>();
    final PriorityQueue<WeightedNode> pendingVertices = new PriorityQueue<>();

    pendingVertices.add(new WeightedNode(0,0));
    for(int i=1; i<graph.links.length; i++) {
      final WeightedNode wn = new WeightedNode(i);
      aux.put(i, wn);
      pendingVertices.add(wn);
    }

    int u = 0;
    visited.add(u);
    while(visited.size()<graph.links.length) {
      // wNode = node minimum weight to visit
      final WeightedNode wNode = pendingVertices.poll();
      visited.add(wNode.node);

      // add wNode to graph
      mstGraph.addLink(wNode.linkFromPrevious);

      // for(w : all neighbours of wNode) {
      //    if(weight of link from wNode to w < w.weight) {
      //        w.weight = Min(w.weight, weight link from wNode to w)
      //        w.linkToPrevious = link from wNode to w
      //    }
      // }
      for(final Link link : graph.links[wNode.node]) {
        //if(visited.contains(link.to)) return;
        final WeightedNode wNeighbour = aux.get(link.to);
        final int newWeight = link.weight + wNode.weight;
        if(wNeighbour.weight > newWeight) {
          wNeighbour.weight = newWeight;
          wNeighbour.linkFromPrevious = link;
        }
      }

      // Force a reheapify
      if(!pendingVertices.isEmpty()) {
        pendingVertices.add(pendingVertices.remove());
      }
    }

    return mstGraph;
  }




  public static void main(final String arg[]) throws Exception {
    final Graph graph = new Graph(5);
    graph.addLink(0,1,2);
    graph.addLink(1,2,3);
    graph.addLink(0,3,6);
    graph.addLink(1,3,8);
    graph.addLink(1,4,5);
    graph.addLink(2,4,7);

    final Graph mst = primMST(graph);
    System.out.println(mst);
  }
}
