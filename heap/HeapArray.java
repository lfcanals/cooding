/**
 * Heap implemented as an array. Minimum value at index 0.
 */
public class HeapArray {
  private final int elements[];
  private int size;
  public HeapArray(final int elements[]) {
    this.elements = elements;
    this.size = this.elements.length;
  }

  public int[] sort() {
    for(int j=size/2-1; j>=0; j--) minDownHepify(j);
    System.out.print("After heapifing:"); dump();
    for(int j=0; j<elements.length-1; j++) getAndRemoveMin();
    return this.elements;
  }


  public int getMin() { return elements[0]; }

  public int getAndRemoveMin() {
    swap(0, size-1);  // Remove the node and place in the car trunk
    size--;
    minDownHepify(0);
    return elements[size];
  }

  /**
   * A little silly function, since it does not remove the i-th element
   * in the order of the heap; just the element at i-th position
   * in the ARRAY that internally represents the heap.
   * I don't know why, but it seems is useful. I cannot find the utility.
   * If I want to remove or get the th-element, I need to iterate over
   * th unsorted heap array, so O(N) at the best.
   * Anyway, this method is O(logN) and what it does is to reheapify
   * after removing an element.
   */
  public int removeAtIndex(int i) {
    swap(i, size-1);
    size--;
    if(elements[getParent(i)]>elements[i]) upHeapify(i); 
    else minDownHepify(i);
  }

  private void minUpHeapify(int pos) {
    if(pos==0) return;
    int pos2 = pos;
    while(pos2!=0) {
      int parentIndex = getParent(pos);
      if(elements[parentIndex]>elements[pos]) {
        swap(parentIndex, pos);
        pos2 = parentIndex;
      } else break;
    }
  }

  private void minDownHeapify(int pos) {
    System.out.print("minhepifying: "+pos+":"); dump();
    if(!isLeaf(pos)) {
      final int right = getRightChild(pos);
      final int left = getLeftChild(pos);
      if(elements[pos]>elements[left] || 
              (right<size && elements[pos]>elements[right])) {
        if(right<size && elements[right] < elements[left]) {
          swap(pos, right); minDownHepify(right);
        } else {
          swap(pos, left); minDownHepify(left);
        }
      }
    }
  }

  private int getParent(int index) { return (index-1)/2; }
  private int getRightChild(int index) { return 2*(index+1); }
  private int getLeftChild(int index) { return 2*(index+1)-1; }
  private boolean isLeaf(int index) { return 2*(index+1)>size; }

  private void swap(int i, int j) { int t=elements[i]; elements[i]=elements[j]; elements[j]=t; }

  public void dump() {
    for(int i=0; i<size; i++) System.out.print(elements[i] + " ");
    System.out.println();
  }



  // The other usage: insert one by one.

  public HeapArray(final int capacity) {
    this.elements = new int[capacity];
    this.size = 0;
  }



  /* Insert O(log n) */
  public void insert(int value) {
    elements[size] = value;
    int current = size;
    int parent = getParent(current);
    while(elements[current] < elements[parent]) {
      int t = elements[current]; 
      elements[current] = elements[parent]; 
      elements[parent] = t;
      current = getParent(current);
      parent = getParent(current);
    }
    size++;
  }


 
}
