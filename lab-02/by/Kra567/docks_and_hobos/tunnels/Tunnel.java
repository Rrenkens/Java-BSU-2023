package by.Kra567.docks_and_hobos.tunnels;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public  class  Tunnel <S> {
    private int maxCount;
    private AtomicReferenceArray<S> storage;
    // [start;end)
    private AtomicInteger start;
    private AtomicInteger end;
    public Tunnel(int maxCount){
        this.maxCount = maxCount;
        this.storage = storage;
        this.start = new AtomicInteger(0);
        this.end = new AtomicInteger(0);
    }

    public int


    public void add(S ship) {
        if ()
    }


    public synchronized S delete() {
        while (true){
            if (currentCount.get() > 0){
                break;
            }
        }
        return storage.delete();
    }


}
