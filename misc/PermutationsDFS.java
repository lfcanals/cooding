import java.util.*;

public class PermutationsDFS {
  public static class NodeInTree {
    public Object[] array;
    public int depth;
    public NodeInTree(final Object array[], final int depth) {
      this.array = array;
      this.depth = depth;
    }

    public String toString() {
      final StringBuilder sb = new StringBuilder();
      for(int i=0; i<array.length;i++) sb.append(array[i]).append(" ");
      return sb.toString();
    }
  }

  public static List<Object[]> permutationsDFS(final Object[] array) {
    final List<Object[]> perms = new ArrayList<>();
    final Deque<NodeInTree> stack = new ArrayDeque<>();

    NodeInTree node = new NodeInTree(array,0);
    stack.push(node);
    while(!stack.isEmpty()) {
      node = stack.pop();
      if(node.depth<array.length-1) {
        for(int i=node.depth; i<array.length; i++) {
          final Object[] array2 = new Object[array.length];
          System.arraycopy(node.array, 0, array2, 0, array.length);
          array2[node.depth] = node.array[i];
          array2[i] = node.array[node.depth];
          final NodeInTree nn = new NodeInTree(array2,node.depth+1);
          stack.push(nn);
        }
      } else {
        perms.add(node.array);
      }
    }
    return perms;
  }
  

  public static void main(final String args[]) {
    final String[] array = { "A", "B", "C", "D" };
    final List<Object[]> perms = permutationsDFS(array);
    outter: for(int i=0;i<perms.size()-1; i++) {
      inner:for(int j=i+1; j<perms.size(); j++) {
        StringBuilder sb = new StringBuilder();
        for(int k=0; k<array.length; k++) {
          sb.append(perms.get(j)[k]).append(" ");
          if(perms.get(i)[k] != perms.get(j)[k]) continue inner;
        }
        System.out.println(i + " and " + j + " are the same: " + sb);
        break outter;
      }
    }
  }
}
