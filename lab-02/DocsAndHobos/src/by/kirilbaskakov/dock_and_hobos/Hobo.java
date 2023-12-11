package by.kirilbaskakov.dock_and_hobos;
public class Hobo implements Runnable {
    private int id;
    private Hobos hobos;
    private int stealingTime;

    public Hobo(int id, int stealingTime, Hobos hobos) {
        this.id = id;
        this.stealingTime = stealingTime;
        this.hobos = hobos;
    }

    @Override
    public void run() {
        while (!hobos.isCooked()) {
            hobos.stealFromDock(id);
            try {
                Thread.sleep(stealingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
