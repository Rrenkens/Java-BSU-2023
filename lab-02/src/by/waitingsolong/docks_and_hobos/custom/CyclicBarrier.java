package by.waitingsolong.docks_and_hobos.custom;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrier {
    private final int parties;
    private static int generation = 0;
    private int waiting = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Runnable barrierAction;

    public CyclicBarrier(int parties) {
        this.parties = parties;
        this.barrierAction = null;
    }

    public CyclicBarrier(int parties, Runnable barrierAction) {
        this.parties = parties;
        this.barrierAction = barrierAction;
    }

    public void await() {
        lock.lock();
        try {
            waiting++;
            if (waiting == parties) {
                if (barrierAction != null) {
                    barrierAction.run();
                }
                waiting = 0;
                generation++;
                condition.signalAll();
            } else {
                int currGeneration = generation;
                while (waiting < parties && generation == currGeneration) {
                    condition.await();
                }
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
