import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore {
	
    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
        // TODO - you fill in here
    	lock = new ReentrantLock(fair);
    	count = new SimpleAtomicLong(permits);
    	condition = (ConditionObject) lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
    	while(count.get()<=0)
    	{
    		condition.await();
    	}
    	lock.lockInterruptibly();
    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
    	while(count.get()<=0)
    	{
    		try {
				condition.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	lock.lock();
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
    	if(count.decrementAndGet()==0)
    	{
    		// set condition
    		condition.signal();
    		lock.unlock();
    	}
    }

    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    ReentrantLock lock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    Condition condition;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here
    SimpleAtomicLong count;
}

