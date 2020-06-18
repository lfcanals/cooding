public class SkipList<K extends Comparable<K>, T> {
    public static class Node<K extends Comparable<K>,T>{
      public Node(final K key, T value, int levels) { 
        this.key = key; this.value = value;
        this.next = new Node[levels];
      }
      public K key; public T value;
      public Node<K,T>[] next;
      public Node<K,T> previous;

      public void adjustLevel(final int newLevel) { // newLevel > level
        final Node<K,T> oldForward[] = this.next;
        this.next = new Node[newLevel+1]; for(int i=0;i<oldForward.length;i++) this.next[i] = oldForward[i];
      }
    }


    public Node<K,T> head;
 
    public SkipList() { this.head = new Node<>(null,null,1);}
 
    public void insert(final K key, final T value) {
        final int newLevel = randomLevel();
    
        if(newLevel>this.head.next.length) {      
          this.head.adjustLevel(newLevel);
        }
    
        Node<K,T> x = head;
        for(int i=this.head.next.length-1; i>=0; i--) {
            while(x.next[i] != null && key.compareTo(x.next[i].key)>0) {
                x=x.next[i];
            }
        }
    
        if(x.next[0]!=null && x.next[0].key!=null 
                && x.next[0].key.compareTo(key)==0) { 
            x.next[0].value = value; 
            return; 
        }
        final Node<K,T> newNode = new Node<>(key, value, newLevel);
        newNode.previous = x;
        for(int i=0; i<newLevel; i++) {
            while(i>=x.next.length) x = x.previous;
            newNode.next[i] = x.next[i];
            if(newNode.next[i]!=null) newNode.next[i].previous = newNode;
            x.next[i] = newNode;
        }
    } 
  
    private int randomLevel() { int lev; for(lev=1; lev<=this.head.next.length+1 && Math.random()<0.5; lev++); return lev;  } 



    public String toString() {
      final StringBuilder sb = new StringBuilder();
      Node<K,T> x = this.head;
      while(x!=null) {
        sb.append("Node : ").append(x.value);
        sb.append(", Links to nodes ");
        for(int i=0; i<x.next.length; i++) {
            if(x.next[i]!=null) sb.append(x.next[i].value);
            else sb.append("(null)");
            sb.append(" ");
        }
        sb.append("\n");
        x = x.next[0];
      }
      return sb.toString();
    }


    public static void main(final String args[]) throws Exception {
      SkipList<Integer, String> skipList = new SkipList();
      skipList.insert(3, "TRES");
      skipList.insert(8, "OCHO");
      skipList.insert(1, "UNO");
      skipList.insert(4, "CUATRO");
      skipList.insert(9, "NUEVE");
      skipList.insert(5, "CINCO");
      skipList.insert(7, "SIETE");
      skipList.insert(6, "SEIS");
      skipList.insert(7, "siete");

      System.out.println(skipList);
    }
}

