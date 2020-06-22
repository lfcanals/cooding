import java.util.*;

public class Radix {
  public static int[] lsd(final int[] array) {
    final List<Integer>[] map = new List[10];

    for(int i=0; i<map.length; i++) map[i] = new ArrayList<>();

    boolean moreDigits = true;
    for(int k=0; moreDigits; k++) {
      final int pow100 = (int) Math.pow(10, k+1);
      final int pow10 = (int) Math.pow(10, k);
      // Classify all elements into arrays
      moreDigits = false;
      for(int i=0; i<array.length; i++) {
        if(pow10<=array[i]*10) moreDigits = true;
        map[(array[i] % pow100)/pow10].add(array[i]);
      }

      if(moreDigits) {
        for(int i=0, l=0; i<map.length; i++) {
          for(int j=0; j<map[i].size(); j++,l++) {
            array[l] = map[i].get(j);
          }
          map[i].clear();
        }
      }
    }
    return array;
  }
  


  public static void main(final String args[]) {
    final int[] arr = new int[] { 1,10,100,99,9,89,74,3, 100};
    lsd(arr);

    for(int i=0; i<arr.length; i++) System.out.print(arr[i]+" ");
    System.out.println();
  }
}
