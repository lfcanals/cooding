import java.util.*;

/**
 * Get the k-highest values of an array of numbers.
 *
 *
 * The ide behind:
 *      Quasy quick-sort:
 *
 *          Search pivot and place values higher to one side
 *                                        smaller to other side
 *          If size of highest is K => Hurra!!
 *          If size of highest > K => reaply to highest
 *          If size highest < K => take missing elements from smallest:
 *                          i.e. look for k-size(highest) top elements in
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


    
