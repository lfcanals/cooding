public class LinkedList {
  public Node head;
  public Node tail;
  public int size;
  public void add(final Node n) {
    n.prev = tail;
    n.next = null;
    if(head==null) {
      head = n;
    } else {
      tail.next = n;
    }
    tail = n;
    this.size++;
  }

  public Node removeHead() {
    if(this.head==null) return null;
    Node n = this.head;
    this.head = this.head.next;
    if(this.head==null) this.tail = null;
    return n;
  }

  public void concat(final LinkedList l) {
    if(l.head==null) return;
    if(head==null) {
      head = l.head;
      tail = l.tail;
    } else {
      tail.next = l.head;
      l.head.prev = tail;
      tail = l.tail;
    }
  }

  public void empty() {
    head = null;
    tail = null;
    size = 0;
  }

  public String toString() {
    Node n = head;
    final StringBuilder buffer = new StringBuilder();
    while(n!=null) {
      buffer.append(n.value).append(" ");
      n = n.next;
    }
    return buffer.toString();
  }
}
