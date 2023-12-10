import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Hobos implements Runnable{
    static long eating_time;
    long stealing_time;

    static long eat = 0;

    static long ID = 0;
    long ID_;

    static long[] ingredients_count;
    static AtomicLong[] curr_ingredients_count;

    static boolean check_ingridients() {
        for (int i = 0; i < curr_ingredients_count.length; ++i) {
            if (curr_ingredients_count[i].get() < ingredients_count[i]) {
                return false;
            }
        }
        return true;
    };
    static void steal() {
        long hobos_num = Main.hobos.size();
        ArrayList<Thread> hobos_threads = new ArrayList<>();
        for(long i = 0; i < hobos_num - 2; ++i) {
            hobos_threads.add(new Thread());
        }

        while(!Thread.currentThread().isInterrupted()) {
            long a, b;
            a = Generator.generate(hobos_num);
            b = Generator.generate(hobos_num);
            while (a == b) {
                b = Generator.generate(hobos_num);
            }
            System.out.println("Start to steal!!!");
            for(int i = 0, j = 0, size = Main.hobos.size(); i < size; ++i) {
                if (i != a && i != b) {
                    hobos_threads.set(j, new Thread(Main.hobos.get(i)));
                    hobos_threads.get(j).start();
                    ++j;
                }
            }
            while (!check_ingridients()) {}
            System.out.println("Time to eat!!!");
            ++eat;
            for (var thread : hobos_threads) {
                thread.interrupt();
            }
            for (int i = 0; i < curr_ingredients_count.length; ++i) {
                curr_ingredients_count[i].set(0);
            }
            try {
                Thread.sleep(eating_time);
            } catch (InterruptedException e) {
                System.out.println("Oops Hobos");
                Thread.currentThread().interrupt();
            }
        }

    }

    @Override
    public void run() {
        System.out.println("Start stole " + this);
        while (!Thread.currentThread().isInterrupted()) {
            int ing = Generator.generate(ingredients_count.length);
            Dock dock = Main.docs.get(Generator.generate(Main.docs.size()));
            while (curr_ingredients_count[ing].get() < ingredients_count[ing] &&
                    dock.curr_dock_capacities[ing].get() > 0 &&
                    !Thread.currentThread().isInterrupted()) {
                curr_ingredients_count[ing].getAndIncrement();
                dock.curr_dock_capacities[ing].getAndDecrement();
                try {
                    Thread.sleep(stealing_time);
                } catch (InterruptedException e) {
                    System.out.println("Oops Hobos");
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("End stole " + this);
    }

    Hobos(long stealing_time) {
        this.stealing_time = stealing_time;
        ID_ = ++ID;
    }

    @Override
    public String toString() {
        return "Hobos: {" + ID_ + "}";
    }
}
