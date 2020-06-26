/**
 * Kunth-Morris-Pratt algo for searching a substring.
 *
 * The idea is:
 *    Let be F[i] an integer array of indexes s.t. F[i]=status previous to i
 *    Create F=[...] to be used in the following algo:
 *    
 *    i,j=0;    // j=status of machine, i=position in string
 *    while(i<string.length) {
 *      if(string[i] == pattern[j]) {
 *        j++;
 *        i++;
 *        if(j==pattern.length) return i; // We win
 *      } else if(j>0) {
 *        j = F[j]; // Try to move to previous status of search
 *      } else {
 *        i++;  // Try to move to next character, the status of search is
 *              // "nothing found"
 *      }
 *    }
 */
public class KnuthMorrisPratt {
  public static int substring(final String string, final String pattern) {
    final int F[] = createPreviousStatus(pattern);
    int i=0,j=0;    // j=status of machine, i=position in string
    while(i<string.length()) {
      if(string.charAt(i) == pattern.charAt(j)) {
        j++;
        i++;
        if(j==pattern.length()) return i-pattern.length(); // We win
      } else if(j>0) {
        j = F[j]; // Try to move to previous status of search
      } else {
        i++;  // Try to move to next character, the status of search is
              // "nothing found"
      }
    }
    return -1;
  }


  /**
   * Create an index of "previous states of search".
   *
   * For example, if pattern is p1,p2,...,pN
   *    (For the example I'm going to consider we are at the begining of 
   *      the string a1)
   *    Status initial (0) is "nothing matches: expecting"
   *    Status (1) is matching p1, the previous in case of failure is 0
   *    Status (2) is matching p1,p2; if next letter fails,
   *               previous status might be or 1 or 0.
   *               It's 1 if p1==p2
   *               We can move to a2 and retry
   *    Status (3) is when matching p1,p2,p3 with a1,a2,a3
   *    Status (4) is when matching p1,p2,p3,p4 with a1,a2,a3,a4
   *               if it fails when it comes from 3, it's becuase a4!=p4
   *               so we will move back to:
   *                2) iff p1=a2 and p2=a3
   *                1) iff p1=a3
   *                0) othercase
   *               as we know (we come from 3):
   *                  a1=p1, a2=p2, a3=p3, we can rewrite:
   *                2) iff p1=p2, p2=p3
   *                1) iff p1=p3
   *                0) other case
   *               and we can move to a2,a3... and retry
   *
   *    Status (k) is when matchinig p1...pk to a1...ak
   *               if it fails, ak!=pk then we move back to:
   *                iff p1=p2, p2=p3, p3=p4, ... pr=pr+1 to k-1
   *                iff p1=p3, p2=p4, p3=p5, ... pr=pr+2 to k-2
   *                iff p1=p(1+s), p2=p(2+s) ... pr=p(r+s) r+s=k-1
   *                and so...
   *
   *    ...
   */
  private static int[] createPreviousStatus(final String pattern) {
    final int F[] = new int[pattern.length()];
    F[0] = 0;
    F[1] = 0;
    outter: for(int k=2; k<F.length; k++) {
      F[k] = 0;
      for(int s=1; s<k-1; s++) {
        boolean aMatch = true;
        for(int j=0; j<s; s++) {
          if(pattern.charAt(j) != pattern.charAt(pattern.length()-s+j)) {
            aMatch = false;
            break;
          }
        }
        if(aMatch) {
          F[k] = s;
          continue outter;
        }
      }
    }
    return F;
  }

  public static void main(final String args[]) {
    final String pattern = "orris";
    final String string = "Porsche, Mini, Morris, Alfa Romeo";

    final int index = substring(string, pattern);
    System.out.println("Substring " + pattern  
        + (index!=-1 ? " present at position " + index : " not present") 
        + " in " + string);

    assert(string.indexOf(pattern) == substring(string, pattern));
    assert("ABBCBDBBEBDBEBDEE".indexOf("EDBEBDEE") 
          == substring("ABBCBDBBEBDBEBDEE", "EDBEBDEE"));


  }
}
