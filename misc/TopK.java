import java.util.*;

/**
 * Get the k-highest values of an array of numbers.
 */
public class TopK {
  private static final Random rand = new Random();

  public static List<Integer> topK(final List<Integer> array, final int k) {
    final int v = array.get(rand.nextInt(array.size()));

    final List<Integer> smaller = new ArrayList<>();
    final List<Integer> bigger = new ArrayList<>();
    for(int i=0; i<array.size(); i++) {
      if(array.get(i)<v) smaller.add(array.get(i));
      if(array.get(i)>v) bigger.add(array.get(i));
    }

    if(bigger.size()==k) {
      return bigger;
    } else if(bigger.size()+1==k) {
      bigger.add(v);
      return bigger;
    } else if(bigger.size()>k) {
      return topK(bigger,k);
    } else {
      bigger.add(v);
      bigger.addAll(topK(smaller, k-bigger.size()));
      return bigger;
    }
  }

  public static void main(final String args[]) {
    final List<Integer> set1 = new ArrayList<>();

    final int k = rand.nextInt(200) + 1;
    final int total = k + rand.nextInt(200);
    for(int i=0; i<total; i++) set1.add(rand.nextInt());

    final List<Integer> topK = topK(set1, k);

    assert(topK.size()==k);
    Collections.sort(set1);
    Collections.sort(topK);
    for(int i=0; i<k; i++) {
      assert(set1.get(set1.size()-1-i) == topK.get(topK.size()-1-i));
    }
  }
}


    
