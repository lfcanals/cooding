public class HeapSort {

  // parent is the less.
  public static void hsort(final int[] array) {
    // O(nlogn) Theta(n)
    int L = array.length;
    for(int i=array.length/2-1; i>=0; i--) downHeapify(array, i, L);

    // O(nlogn) again
    for(int i=0; i<array.length-1; i++) {
      swap(array, 0, L-1);
      L--;
      downHeapify(array, 0, L);
    }
  }

  public static void swap(final int[] array, final int j, final int i) {
    final int t = array[i];
    array[i] = array[j];
    array[j] = t;
  }

  public static void downHeapify(final int[] array, final int i, final int L) {
    // Checks heap property whit my children, swap and reheapify
    int index = i;
    while(!isLeaf(index,L)) {
      final int left = leftChild(index);
      final int right = rightChild(index);

      if((right<L && array[right]<array[index]) || (array[left]<array[index])) {
        if(right<L && array[right]<array[left]) {
          swap(array, index, right);
          index = right;
        } else {
          swap(array, index, left);
          index = left;
        }
      } else { 
        return;
      }
    }
  }


  public static void upHeapify(final int[] array, final int i, final int L) {
    // Checks heap property with my parent, swap if needed and reHeapify
    int index = i;
    while(index>0) {
      final int parent = parent(i);
      if(array[i]<array[parent]) {
        swap(array, i, parent);
        index = parent;
      } else {
        return;
      }
    }
  }

  public static int leftChild(int i) { return (i+1)*2-1; }
  public static int rightChild(int i) { return (i+1)*2; }
  public static int parent(int i) { return (i-1)/2; }
  public static boolean isLeaf(int i, int L) { return (i+1)*2>L; }



  public static void main(final String args[]) {
    final int[] array = { 1,4,3,2,4,5,67,-1,9,0};
    hsort(array);

    for(int i=0; i<array.length; i++) System.out.print(array[i] + " ");
    System.out.println();
  }
}
