import java.util.*;

/**
 * To generate permutations iteratively:
 *
 *  0 1 2 3 4 5 6 ... N-1   indexes
 *  L L L L L L L      L    directions
 *
 *  Find the index "mobile" which value is greater than the immediate on the
 *    "direction" (L left, R right)
 *  Swap following the direction
 *
 *  If there is no mobile, change direction of index at position 0
 *  if 0 is L or N-1 if N-1 is R.
 *
 */
public class JohnsonTrotter {
  private static final boolean LEFT = false, RIGHT = true;

  public static void swap(final int[] arr, final int i, final int j) {
    final int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
  }
  public static void swap(final boolean[] arr, final int i, final int j) {
    final boolean t = arr[i]; arr[i] = arr[j]; arr[j] = t;
  }



  public static List<Object[]> johnsonTrotter(final Object[] array) {
    int fact = 1;
    final int index[] = new int[array.length];
    for(int i=1; i<=array.length; i++) {
      fact *= i;
      index[i-1] = i-1;
    }

    // false toLEFT, true toRIGHT
    final boolean[] directions = new boolean[array.length];
    final List<Object[]> perms = new ArrayList<>();
    perms.add(array);

    for(int i=1; i<fact; i++) {
      int mobile = findMobile(index, directions);

      if(directions[mobile] == LEFT) {
        swap(index, mobile, mobile-1);
        swap(directions, mobile, mobile-1);
      } else {
        swap(index, mobile, mobile+1);
        swap(directions, mobile, mobile+1);
      }

      final Object[] p = new Object[array.length];
      for(int j=0; j<index.length; j++) {
        p[j] = array[index[j]];
      }
      perms.add(p);
    }

    return perms;
  }

  public static int findMobile(final int[] index, final boolean[] dirs) {
    int mobileIndex = -1;
    for(int i=0; i<dirs.length; i++) {
      if(dirs[i] == LEFT) {
        if(i>=1 && index[i-1]<index[i]) {
          if(mobileIndex==-1 || index[i]>index[mobileIndex]) mobileIndex = i;
        }
      } else {
        if(i<dirs.length-1 && index[i+1]<index[i]) {
          if(mobileIndex==-1 || index[i]>index[mobileIndex]) mobileIndex = i;
        }
      }
    }

    // Flip directions for elements greater than index[mobile]
    for(int i=0; i<index.length; i++) {
      if(index[i]>index[mobileIndex]) dirs[i] = ! dirs[i];
    }
    return mobileIndex;
  }


  public static void main(final String args[]) {
    final String array[] = new String[] { "a","b","c", "d", "e" };
    final List<Object[]> perms = johnsonTrotter(array);

    System.out.println(perms.size() + " permutations");
    for(Object[] line : perms) {
      for(int i=0; i<line.length; i++) System.out.print(line[i]+" ");
      System.out.println();
    }

    // Check
    outter: for(int i=0; i<perms.size(); i++) {
      inner: for(int j=i+1; j<perms.size(); j++) {
        for(int k=0; k<perms.get(i).length; k++) {
          if(perms.get(i)[k] != perms.get(j)[k]) continue inner;
        }
        System.out.println("Elements at " + i + " and " + j + " are equal!");
        break outter;
      }
    }

  }
}

