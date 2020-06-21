import java.util.*;

/**
 * From https://www.geeksforgeeks.org/welsh-powell-graph-colouring-algorithm
 *
 * Colour a graph with minimum numbers of colours.
 */
public class ColouringWelshPowell {
  public static class Graph {
    public List<Integer>[] links;
    public Graph(int numOfNodes) {
      this.links = new List[numOfNodes];
      for(int i=0; i<numOfNodes; i++) {
        this.links[i] = new ArrayList<Integer>();
      }
    }

    public void addLink(final int from, final int to) {
      this.links[from].add(to);
      this.links[to].add(from);
    }

    public String toString() {
      final StringBuilder sb = new StringBuilder("[");
      for(int i=0; i<this.links.length; i++) {
        sb.append(this.links[i]);
        if(i!=this.links.length-1) sb.append(",\n");
      }
      sb.append("]");
      return sb.toString();
    }
  }

  /**
   * @return a list of sets, each set contains nodes with same color.
   */
  public static List<Set<Integer>> welshPowell(final Graph graph) {
    final LinkedList<Integer> nodeList = new LinkedList<>();
    for(int i=0; i<graph.links.length; i++) nodeList.add(i);
    // sort nodes in degree descending order
    Collections.sort(nodeList, new Comparator<Integer>() {
        public int compare(final Integer a, final Integer b) {
          return - (graph.links[a].size() - graph.links[b].size());
        }
        public boolean equals(final Object o) { return o==this; }
      });


    final List<Set<Integer>> result = new ArrayList<>();
    while(!nodeList.isEmpty()) {
      Set<Integer> colour = new HashSet<>();
      result.add(colour);
      
      // Colours node with max degree
      final int node = nodeList.removeFirst();
      colour.add(node);

      // Colour nodes with max degrees and not neighbours of same colour
      allNodes: for(Iterator<Integer> it = nodeList.iterator(); it.hasNext();) {
        final int x = it.next();
        for(int xNeigh : graph.links[x]) {
          if(colour.contains(xNeigh)) continue allNodes;
        }
        it.remove();
        colour.add(x);
      }
    }

    return result;
  }



  public static void main(final String args[]) {
    Graph g = new Graph(11);
    g.addLink(0,1);
    g.addLink(1,3);
    g.addLink(3,2);
    g.addLink(3,8);
    g.addLink(8,9);
    g.addLink(9,10);
    g.addLink(10,4);
    g.addLink(10,4);
    g.addLink(4,5);
    g.addLink(4,5);
    g.addLink(6,7);
    g.addLink(7,0);
    g.addLink(7,8);
    g.addLink(7,9);
    g.addLink(7,10);

    System.out.println(welshPowell(g));
  }
}
