public class Qsort {
  public int[] sort(int[] arr) {
    qsort(arr, 0, arr.length);
    return arr;
  }

  private void qsort(int[] arr, int b, int e) {
    if(b<e) {
      int pivot = pivot(arr,b,e);
      qsort(arr, pivot+1, e);
      qsort(arr, b, pivot);
    }
  }

  public int pivot(int[] arr, int begin, int end) {
    int pivotVal = arr[begin];
    int i = begin, j = end-1;
    while(i<j) {
      while(arr[i]<=pivotVal && i<j) i++;
      while(arr[j]>pivotVal) j--;
      if(i<j) {
        swap(arr, i, j);
      }
    }
    swap(arr,j,begin);
    return j;
  }

  public void swap(int[] arr, int i, int j) {
    final int t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
    numberOfSwaps++;
  }


  int numberOfSwaps;


  public static void main(String args[]) throws Exception {
      int random[] = new int[] { 7,3,5,2,0,9,5,4,3,3,0,9,10,2,1 };

      final Qsort qsort = new Qsort();
      qsort.qsort(random,0,random.length);
      System.out.println("Number of swaps=" + qsort.numberOfSwaps);
      for(int i=0; i<random.length; i++) System.out.print(random[i] + " ");
      qsort.numberOfSwaps = 0;
      qsort.qsort(random,0,random.length);
      System.out.println("Number of swaps already sorted=" + qsort.numberOfSwaps);
      for(int i=0; i<random.length; i++) System.out.print(random[i] + " ");


  }
}
