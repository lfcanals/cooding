import java.util.concurrent.locks.AbstractQueuedSynchronizer;


/**
 * Implementation of a Semaphore using an AbstractQueuedSynchronizer.
 *
 * Interface for AbstractQueuedSyncrhonizer is:
 *    tryAcquireShared(numOfResources);   // called from semaphore.acquire()
 *    tryReleaseShared(numOfResources);   // called from semaphore.release()
 *
 * Internally, it uses AbstractQueuedSyncrhonizer.compareAndSetState/getState
 *    as changes are enqueued.
 *
 * Semaphore uses AbstractQueuedSyncrhonizer:
 *    acquire -> tryAcquireShared(1);
 *    release -> tryReleaseShared(1);
 *
 * The key point is that IS NOT BLOCKING.
 *
 */
public class Semaph2 {
  private static final class Semaphore {
    private class Sync extends AbstractQueuedSynchronizer {
      public Sync(final int available) {
        setState(available);
      }
      public int tryAcquireShared(int x) {
        for(;;) {
          int available = getState();
          int remaining = available - x;
          if(remaining<0 || compareAndSetState(available, remaining)) {
            return remaining;
          }
        }
      }

      public boolean tryReleaseShared(int x) {
        for(;;) {
          int available = getState();
          int remaining = available + x;
          if(compareAndSetState(available, remaining)) return true;
        }
      }
    }

    private final AbstractQueuedSynchronizer sync;
    public Semaphore(final int locks) {
      this.sync = new Sync(locks);
    }
    public void acquire() throws InterruptedException {
      sync.acquireShared(1);
    }
    public void release() throws InterruptedException {
      sync.releaseShared(1);
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
