import java.util.function.*;
import java.util.*;

// Binary
public class Tree<T> {
  public Node<T> root;

  public Tree() { }

  public Tree(final Node<T> root) { this.root = root; }




  // BFS
  public void BFS(Consumer<Node<T>> f) {
    final Queue<Node<T>> queue = new LinkedList<Node<T>>();
    queue.add(root);
    while( ! queue.isEmpty() ) {
        final Node<T> n = queue.remove();
        f.accept(n);
        if(n.child[0]!=null) queue.add(n.child[0]);
        if(n.child[1]!=null) queue.add(n.child[1]);
    }
  }

  // DPS
  public void preorderDFS(Consumer<Node<T>> f) {
    final java.util.Stack<Node<T>> stack = new java.util.Stack<>();
    stack.push(this.root);

    while(!stack.empty()) {
      final Node<T> n = stack.pop();
      f.accept(n);
      if(n.child[1]!=null) stack.push(n.child[1]);
      if(n.child[0]!=null) stack.push(n.child[0]);
    }
  }

  public void inorderDFS(Consumer<Node<T>> f) {
    final java.util.Stack<Node<T>> stack = new java.util.Stack<>();

    Node<T> n = root;
    while(n!=null || !stack.empty()) {
      if(n!=null) {
        stack.push(n);
        n = n.child[0];
      } else {
        n = stack.pop();
        accept(n);
        n = n.child[1];
      }
    }
  }


  public void postorderDFS(Consumer<Node<T>> f) {
    final java.util.Stack<Node<T>> stack = new java.util.Stack<>();

    Node<T> prev = null;
    stack.push(root);
    while(!stack.empty()) {
      final Node<T> n = stack.peek(); // Don't remove!
      if(prev==null || prev.child[0]==n || prev.child[1]==n) {
        if(n.child[0]!=null) stack.push(n.child[0]);
        else if(n.child[1]!=null) stack.push(n.child[1]);
        else {
          // A leaf
          f.accept(stack.pop());  // Remove the top: stack.peek()==stack.pop()
        }
      } else if(n.child[0] == prev) {
        if(n.child[1]!=null) stack.push(n.child[1]);
        else {
          // A leaf
          f.accept(stack.pop());
        }
      } else if(n.child[1] == prev) {
        f.accept(stack.pop());
      }
      prev = n;
    }
  }


}
