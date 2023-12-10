package by.waitingsolong.docks_and_hobos.custom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Semaphore {
    private int permits;
    private boolean fair;
    private ReentrantLock lock;
    private Condition condition;

    public Semaphore(int permits, boolean fair) {
        this.permits = permits;
        this.fair = fair;
        this.lock = new ReentrantLock(fair);
        this.condition = lock.newCondition();
    }

    public Semaphore(int permits) {
        this(permits, false);
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits == 0) {
                condition.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public void acquireUninterruptibly() {
        lock.lock();
        try {
            while (permits == 0) {
                condition.awaitUninterruptibly();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public boolean tryAcquire() {
        
        lock.lock();
        try {
            if (permits > 0) {
                permits--;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            permits++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}


