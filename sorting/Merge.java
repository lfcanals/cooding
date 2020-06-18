public class Merge {
  public int[] sort(final int[] arr) {
    mergeSort(arr, 0, arr.length-1);
    return arr;
 }


 private void mergeSort(final int[] arr, int b, int e) {
   if(b<e) {
     int m = (b+e)/2;
     mergeSort(arr,b,m);
     mergeSort(arr,m+1,e);
     merge(arr,b,m,e);
   }
 }

 private void merge(final int[] arr, int b, int m, int e) {
   final int[] arrL = new int[m-b+1];
   final int[] arrR = new int[e-m];
   System.arraycopy(arr,b,arrL,0,arrL.length);
   System.arraycopy(arr,m+1,arrR,0,arrR.length);

   int i=0; int j=0;
   int k;
   for(k=b; i<arrL.length && j<arrR.length; k++) {
     if(arrL[i] <= arrR[j]) {
       arr[k] = arrL[i];
       i++;
     } else {
       arr[k] = arrR[j];
       j++;
     }
   }
   System.arraycopy(arrL,i,arr,k,arrL.length-i);
   k += arrL.length-i;
   System.arraycopy(arrR,j,arr,k,arrR.length-j);
 }
}
