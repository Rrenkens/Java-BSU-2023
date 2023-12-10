package by.Dzenia.docks_and_hobos.RunnableObjects;

import by.Dzenia.docks_and_hobos.Controller.Model;
import by.Dzenia.docks_and_hobos.Persons.Ship;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class ShipGenerator implements Runnable {
    private final int generationTime;
    private final Timer timer;
    private final int shipCapacityMin;
    private final int shipCapacityMax;
    private final Model model;

    public ShipGenerator(int generationTime, int shipCapacityMin, int shipCapacityMax, Model model) {
        if (generationTime <= 0) {
            throw new IllegalArgumentException("Generation time must be positive");
        }
        if (shipCapacityMin <= 0 || shipCapacityMax < shipCapacityMin) {
            throw new IllegalArgumentException("Bounds of capacity is incorrect");
        }
        this.generationTime = generationTime;
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
        this.model = model;
        this.timer = new Timer();
    }


    @Override
    public void run() {
//        Random random = new Random();
//        random.nextInt(0, 2);
        TimerTask generateShip = new TimerTask() {
            @Override
            public void run() {
//                random.nextInt(0, 1);
                Ship ship = new Ship(
                        model.getCargos().get(ThreadLocalRandom.current().nextInt(0, model.getCargos().size())).getType(),
                        ThreadLocalRandom.current().nextInt(shipCapacityMin, shipCapacityMax)
                );
                System.out.println("New ship with cargo=" + ship.getCargo().getType() + ", weight=" + ship.getWeight());
                model.getTunnel().addShip(ship);
            }
        };

        timer.scheduleAtFixedRate(generateShip, 0, generationTime * 1000L);
    }
}
