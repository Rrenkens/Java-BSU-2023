package by.Kra567.docks_and_hobos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public  class  Tunnel <S,T extends ShipStorage<S>> implements ShipStorage<S> {
    private int maxCount;
    private T storage;
    private AtomicInteger currentCount;

    Tunnel(T storage,int maxCount){
        this.maxCount = maxCount;
        this.storage = storage;
        this.currentCount = new AtomicInteger(storage.count());
    }

    @Override
    public void add(S ship) {
        if (currentCount.get() < maxCount){
            synchronized (this){
                storage.add(ship);
                currentCount.incrementAndGet();
            }
        }
    }

    @Override
    public synchronized S delete() {
        while (true){
            if (currentCount.get() > 0){
                break;
            }
        }
        return storage.delete();
    }

    @Override
    public int count() {
        return 0;
    }
}
