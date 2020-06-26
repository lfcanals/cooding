/**
 * Rabin-Karp algo for searching a substring.
 *
 * The idea is:
 *  1.- Calculate a hash for the substring to search.
 *  2.- Iterate over the string calculating the hash and compare them before
 *      check every character.
 */
public class RabinKarp {
  public static int substring(final String string, final String pattern) {
    final int M = 1000000;
    int patternHash = 0;
    for(int i=0; i<pattern.length(); i++) {
      patternHash = (patternHash 
          + Character.getNumericValue(pattern.charAt(i))) % M;
    }

    int stringHash = 0;
    for(int i=0; i<pattern.length(); i++) {
      stringHash = (stringHash 
          + Character.getNumericValue(string.charAt(i)));
    }

    for(int i=0;i<string.length()-pattern.length(); i++) {
      if(stringHash==patternHash) {
        boolean aMatch = true;
        for(int j=0; j<pattern.length(); j++) {
          if(pattern.charAt(j)!=string.charAt(i+j)) {
            aMatch = false;
            break;
          }
        }
        if(aMatch) return i;
      }

      stringHash = (stringHash - Character.getNumericValue(string.charAt(i)))%M;
      stringHash = (stringHash + Character.getNumericValue(string.charAt(
              i + pattern.length()))) % M;
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
  }
}
