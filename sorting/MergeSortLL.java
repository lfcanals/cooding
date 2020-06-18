public class MergeSortLL {
  public static void sort(final LinkedList list) {
    if(list.size<=1) return;
    int m = list.size/2;
    Node n = list.head;  for(int i=1; i<m; i++)  n = n.next;
    final LinkedList left = new LinkedList();
    final LinkedList right = new LinkedList();
    left.head = list.head; right.head = n.next;
    left.tail = n; right.tail = list.tail;

    n.next = null; right.head.prev = null;

    right.size = list.size - m;
    left.size = m;

    sort(left);
    sort(right);

    // merge left, right over list
    Node leftNode = left.removeHead();
    Node rightNode = right.removeHead();
    list.empty();
    while(leftNode!=null && rightNode!=null) {
      if(leftNode.value < rightNode.value) {
        list.add(leftNode);
        leftNode = left.removeHead();
      } else {
        list.add(rightNode);
        rightNode = right.removeHead();
      }
    }
    if(leftNode!=null) list.add(leftNode);
    if(rightNode!=null) list.add(rightNode);

    list.concat(left);
    list.concat(right);
  }

  public static void main(final String args[]) {
    final LinkedList list = new LinkedList();
    Node n = new Node(3); list.add(n);
    n = new Node(3); list.add(n);
    n = new Node(7); list.add(n);
    n = new Node(8); list.add(n);
    n = new Node(4); list.add(n);
    n = new Node(3); list.add(n);
    n = new Node(1); list.add(n);
    sort(list);

    n = list.head;
    while(n!=null) {
      System.out.print(n.value + " ");
      n = n.next;
    }
    System.out.println();

    System.out.println("It should be 1 3 3 4 7 8");
  }
}
