import java.util.*;

public class Astar {
  public static class WeightedVertex {
      public final double weight;
      public final int destiny;
      public WeightedVertex(final int destiny, final double weight) {
        this.weight = weight;
        this.destiny = destiny;
      }
  }


  public List<WeightedVertex>[] vertex;
  public int numberOfNodes;


  public Astar(final int numberOfNodes) {
    this.numberOfNodes = numberOfNodes;
    this.vertex = new List[numberOfNodes];
    for(int i=0; i<numberOfNodes; i++) {
      this.vertex[i] = new ArrayList<WeightedVertex>();
    }
  }

  private final class Heuristic {
    public double computeCost(final int origin, final int destiny) {
      return 0; // Just a simple Dijkstra...
    }
  }

  
  private final static class RouteNode implements Comparable<RouteNode> {
    public final int currentNode;
    public int previousNode;
    public double routeCostFromOrigin, estimatedCostToDestiny;

    public RouteNode(final int currentNode, final int previousNode, 
        final double routeCostFromOrigin, final double estimatedCostToDestiny) {
      this.currentNode = currentNode;
      this.previousNode = previousNode;
      this.routeCostFromOrigin = routeCostFromOrigin;
      this.estimatedCostToDestiny = estimatedCostToDestiny;
    }

    @Override
    public int compareTo(final RouteNode right) {
      return (int) (this.estimatedCostToDestiny - right.estimatedCostToDestiny);
    }
  }

  public List<Integer> aStarMinDistances(final int startNode,final int endNode){
    /*
     * Shortest path between START and END

      // VisitedNode type has: 
      //        currentNode, previousNode, := none
      //        realCostFromStart       := inf
      //        estimatedCostToEnd      := inf

      // sorted by estimated cost to END
      openSet := new PriorityQueue< VisitedNodes >(); 

      // route is a way to map visited nodes to VisitedNodes

      openSet.add(START);   // 0 distance to START, h(END) to end

      while(openSet not empty) {
        node = openSet.poll();    // Pickup the first
        if(node==END) {
          // Arrived.
          return reversePath(route);
        } else {
          for( n in node.neighbours ) {
            x = route.get(node);    
            if( x==null ) route.put(node, x=new VisitedNode(
                            node:n, previous:node, 
                            realCostFromStart:INF, estimatedCostToEnd:INF));
            if( node.costFromStart + cost(node -> x) < x.costFromStart) {
              update x.costFromStart
              update x.estimatedCostToEnd = x.costFromStart + h(x)
              openSet.add(x);
            }
            route.put(n, x);
          }
        }
      }

      O(E logV)

    */


    final Heuristic heuristic = new Heuristic();

    // Heap sorted by estimatedCostToDestiny
    final Queue<RouteNode> openSet = new PriorityQueue<>();

    final Map<Integer, RouteNode> visitedNodes = new HashMap<>();

    RouteNode start = new RouteNode(startNode, -1, 0d, 
                      heuristic.computeCost(startNode, endNode));
    openSet.add(start);

    while(!openSet.isEmpty()) {
      RouteNode node = openSet.poll();  // Remove the cheapest
      if(node.currentNode == endNode) { // We're arrived!
        final List<Integer> finalRoute = new ArrayList<>();
        // Reverse route
        for(RouteNode x = node; x!=null; x = visitedNodes.get(x.previousNode)) {
          finalRoute.add(x.currentNode);
        }
        return finalRoute;
      } else {
        for(WeightedVertex neighbour : this.vertex[node.currentNode]) {
          final RouteNode n = visitedNodes.getOrDefault(neighbour.destiny, 
              new RouteNode(neighbour.destiny, -1, Double.POSITIVE_INFINITY, 
                                                   Double.POSITIVE_INFINITY));
          final double newCostToN = node.routeCostFromOrigin + neighbour.weight;
          if(newCostToN < n.routeCostFromOrigin) {
            n.routeCostFromOrigin = newCostToN;
            n.estimatedCostToDestiny = newCostToN 
                    + heuristic.computeCost(n.currentNode, endNode);
            n.previousNode = node.currentNode;
            openSet.add(n);
          }
          visitedNodes.put(neighbour.destiny, n);
        }
      }
    }

    return new ArrayList<>();
  }

  public void addLink(final int origin, final int destiny, final int weight) {
    vertex[origin].add(new WeightedVertex(destiny, weight));
  }


  public static void main(final String args[]) throws Exception {
    final Astar graph = new Astar(5);
    graph.addLink(0,1,2);
    graph.addLink(1,2,3);
    graph.addLink(0,3,6);
    graph.addLink(1,3,2);
    graph.addLink(1,4,5);
    graph.addLink(2,4,1);

    for(int dest = 0; dest<5; dest++) {
      final List<Integer> path = graph.aStarMinDistances(0, dest);
      System.out.println(path);
    }
  }
}
