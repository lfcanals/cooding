import java.util.*;

/**
 * To generate permutations iteratively:
 *
 *   given a set, it starts swapping two adjacent elements from 0 to N-1
 *   again and again.
 */
public class JohnsonTrotter {
  public static List<Object[]> johnsonTrotter(final Object[] array) {
    int fact = 1;
    final List<Object[]> perms = new ArrayList<>();
    for(int i=1; i<=array.length; i++) fact *= i;
    perms.add(array);
    int k = 0;
    Object[] previousLine = new Object[array.length];
    System.arraycopy(array,0,previousLine,0,array.length);
    for(int i=1; i<fact; i++) {
      final Object line[] = new Object[previousLine.length];
      for(int j=0; j<line.length;  j++) {
        if(k==j) {
          line[j] = previousLine[j+1];
        } else if(k+1==j) {
          line[j] = previousLine[j-1];
        } else {
          line[j] = previousLine[j];
        }
      }
      perms.add(line);
      previousLine = line;
      k = (k+1) % (array.length-1);
    }
    return perms;
  }

  public static void main(final String args[]) {
    final String array[] = new String[] { "a","b","c","d" };
    final List<Object[]> perms = johnsonTrotter(array);

    System.out.println(perms.size() + " permutations");
    for(Object[] line : perms) {
      for(int i=0; i<line.length; i++) System.out.print(line[i]+" ");
      System.out.println();
    }
  }
}

