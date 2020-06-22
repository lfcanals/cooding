import java.util.concurrent.atomic.AtomicInteger;


public class Semaph3 {
  private static final class Semaphore {
    private class Sync {
      private final AtomicInteger availables = new AtomicInteger();
      public Sync(final int available) {
        availables.set(available);
      }
      public int tryAcquireShared(int x) {
        for(;;) {
          int available = availables.get();
          int remaining = available - x;
          if(remaining<0 
              || availables.compareAndSet(available, remaining)) {
            return remaining;
          }
        }
      }

      public boolean tryReleaseShared(int x) {
        for(;;) {
          int available = availables.get();
          int remaining = available + x;
          if(availables.compareAndSet(available, remaining)) return true;
        }
      }
    }

    private final Sync sync;
    public Semaphore(final int locks) {
      this.sync = new Sync(locks);
    }
    public void acquire() throws InterruptedException {
      sync.tryAcquireShared(1);
    }
    public void release() throws InterruptedException {
      sync.tryReleaseShared(1);
    }
  }


  private final static Semaphore semaphore = new Semaphore(2);


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
