/**
 * Searches the pattern moving from left to right but checking from right to 
 * left.... so crazy! But efficient.
 */
public class BoyerMooleHorspool {
  /**
   * The naive version, just comparing everything but in reverse order.
   */
  public static int substringNaive(final String string, final String pattern) {
    for(int i=0; i<string.length()-pattern.length(); i++) {
      int j=pattern.length();
      for(; j>=0; j--) {
        if(string.charAt(i+j) != pattern.charAt(j)) break;
      }
      if(j<0) return i;
    }
    return -1;
  }

  /**
   * Optimized.
   * Define a shift array: shift[ c ] = length.pattern-pos(c in pattern)-1
   *
   * It's the number of elements to move forward over string in case 
   *  string[i] matches with an element of pattern.
   *
   *  The idea is:
   *  
   *    If pattern is FGHI
   *    And string is ABCDEFGGHFGHI  (string(1)=A and so)
   *
   *    Iterate i=4:
   *        if string(i) is not in pattern, i+=pattern.length
   *        but if string(i) is in pattern, move i=i+k such that
   *            string(i) = pattern(pattern.length)
   *            and then try to check if matches
   */
  public static int substring(final String string, final String pattern) {
    final int shift[] = new int[256];
    for(int i=0; i<shift.length; i++) shift[i] = pattern.length();

    // shift[ p0 ] = p.length - 1
    // shift[ p1 ] = p.length - 2
    // shift[ p2 ] = p.length - 3
    // for the rest p.length
    for(int i=0; i<pattern.length(); i++) {
      shift[((int)pattern.charAt(i))] = 
            pattern.length() - 1 - i;
    }

    for(int i=pattern.length()-1; i<string.length();
        i += shift[((int)string.charAt(i))]) {
      boolean match = true;
      for(int j = pattern.length() - 1; j>=0; j--) {
        if(pattern.charAt(j) != string.charAt(i-pattern.length()+1+j)) {
          match = false;
          break;
        }
      }
      if(match) return i-pattern.length()+1;
    }

    return -1;
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

