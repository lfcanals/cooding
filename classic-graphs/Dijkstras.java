import java.util.*;

/**
 * Idea behind Dijkstra:
 *
 *  Input: graph and startNode
 *  Output: array min distances to each node from startNode
 *
 *  Logic:
 *      Tenemos un conjunto de "nodosVisitados"
 *      nodosVisitados.add(startNode);
 *      currentNode = startNode
 *
 *      Repetir hasta visitar todos nodos:
 *          anotamos en los nodos vecinos de "currentNode"
 *              el camino mas corto hasta ellos desde startNode
 *
 *          currentNode = buscamos entre los vecinos de nodos visitados 
 *              el que acumula el camino 
 *              mas corto desde startNode
 *
 *          nodosVisitados.add(currentNode);    
 *
 */
public class Dijkstras {
  public static class WeightedVertex {
      public final int weight;
      public final int destiny;
      public WeightedVertex(final int destiny, final int weight) {
        this.weight = weight;
        this.destiny = destiny;
      }
  }


  public List<WeightedVertex>[] vertex;
  public int numberOfNodes;


  public Dijkstras(final int numberOfNodes) {
    this.numberOfNodes = numberOfNodes;
    this.vertex = new List[numberOfNodes];
    for(int i=0; i<numberOfNodes; i++) {
      this.vertex[i] = new ArrayList<WeightedVertex>();
    }
  }

  
  public int[] dijkstrasMinDistances(final int startNode) {
    final Set<Integer> shortestPath = new HashSet<>();

    final int[] distances = new int[this.numberOfNodes];
    for(int i=0; i<distances.length; i++) distances[i] = Integer.MAX_VALUE;
    distances[startNode] = 0;

    int currentNode = startNode;
    shortestPath.add(currentNode);
    while(shortestPath.size()<this.numberOfNodes) {
      for(WeightedVertex neighbour : vertex[currentNode]) {
        final int newWeight = neighbour.weight + distances[currentNode];
        if(newWeight<distances[neighbour.destiny]) {
          distances[neighbour.destiny] = newWeight;
        }
      }

      // Choose nect node:
      // Opportunistic approach:only neighbours of used nodes can be considered
      final Set<Integer> considered = new HashSet<Integer>();
      int minDistance = Integer.MAX_VALUE;
      for(Integer usedNode : shortestPath) {
        for(WeightedVertex neighbour : vertex[usedNode]) {
          if(shortestPath.contains(neighbour.destiny)) continue;
          if(distances[neighbour.destiny]<minDistance) {
            minDistance = distances[neighbour.destiny];
            currentNode = neighbour.destiny;
          }
        }
      }

      for(int i=0; i<distances.length; i++) System.out.print(distances[i]+" ");
      System.out.println();
      System.out.println("Next node to add " + currentNode);
      if(shortestPath.contains(currentNode)) break; // No more nodes reachable
      shortestPath.add(currentNode);
    }

    return distances;
  }

  public void addLink(final int origin, final int destiny, final int weight) {
    vertex[origin].add(new WeightedVertex(destiny, weight));
  }


  public static void main(final String args[]) throws Exception {
    final Dijkstras graph = new Dijkstras(5);
    graph.addLink(0,1,2);
    graph.addLink(1,2,3);
    graph.addLink(0,3,6);
    graph.addLink(1,3,2);
    graph.addLink(1,4,5);
    graph.addLink(2,4,1);


    final int[] distancesFromZero = graph.dijkstrasMinDistances(0);
    for(int i=0; i<distancesFromZero.length; i++) {
      System.out.println("Node : "+i+", dist from 0 = "+distancesFromZero[i]);
    }
  }
}
