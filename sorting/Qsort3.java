public class Qsort3 {
  public int[] sort(int[] arr) {
    qsort(arr, 0, arr.length-1);
    return arr;
  }


  public void qsort(int[] arr, int begin, int end) {
    if(begin<end) {
      int pivot = pivot(arr, begin, end);
      qsort(arr, begin, pivot-1);
      qsort(arr, pivot+1, end);
    }
  }

  public int pivot(int [] arr, int begin, int end) {
    int pi = begin - 1;
    int pivot = arr[end];

    for(int j=begin; j<end; j++) {
      if(arr[j]<=pivot) {
        pi++;
        int t=arr[j]; arr[j]=arr[pi]; arr[pi]=t;
      }
    }

    int t=arr[end]; arr[end]=arr[pi+1]; arr[pi+1]=t;

    return pi+1;
  }


  public static void main(final String[] args) throws Exception {
    final Qsort3 qsort = new Qsort3();
    final int[] in = new int[] { 8,10,9,4,5,2,3,4,5,2,0,9,8,2,3,4,-3 };

    final int[] out = qsort.sort(in);

    System.out.println();
    System.out.println("Resulting array:");
    for(int i=0; i<out.length; i++) System.out.print(out[i] + " ");
    System.out.println();
  
}
