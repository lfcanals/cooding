import java.util.concurrent.Semaphore;


public class Semaph1 {
  private final static Semaphore semaphore = new Semaphore(2, true);


  private static final class R implements Runnable {
    private final long delay;
    private final int num;
    public R(final long delay, final int num) {
      this.delay = delay;
      this.num = num;
    }
    public void run() {
      for(;;) {
        try {
          semaphore.acquire();
          try {
            System.out.println("Thread " + num + " in");
            Thread.sleep(delay);
            System.out.println("Thread " + num + " out");
          } finally {
            semaphore.release();
          }
        } catch(Exception e) {
          // Do nothing
        }
      }
    }
  }

  public static void main(final String args[]) throws Exception {
    new Thread(new R(10000, 1)).start();
    new Thread(new R(3000, 2)).start();
    new Thread(new R(1000, 3)).start();
    new Thread(new R(1000, 4)).start();
  }
}
