package by.waitingsolong.docks_and_hobos.custom;

import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrier {
    private final int parties;
    private int waiting = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Runnable barrierAction;
    private boolean reset;

    public CyclicBarrier(int parties) {
        this.parties = parties;
        this.barrierAction = null;
    }

    public CyclicBarrier(int parties, Runnable barrierAction) {
        this.parties = parties;
        this.barrierAction = barrierAction;
    }

    public void await() throws InterruptedException {
        lock.lock();
        try {
            waiting++;
            if (waiting == parties) {
                if (barrierAction != null) {
                    barrierAction.run();
                }
                condition.signalAll();
                Thread.yield();
                reset();
            } else {
                while (waiting < parties) {
                    condition.await();
                }

                if (reset) {
                    throw new BrokenBarrierException();
                }
            }
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void reset() {
        lock.lock();
        try {
            reset = true;
            condition.signalAll();
            waiting = 0;
            Thread.yield();
            reset = false;
        } finally {
            lock.unlock();
        }
    }
}
