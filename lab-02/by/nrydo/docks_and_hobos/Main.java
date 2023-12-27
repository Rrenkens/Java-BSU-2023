package by.nrydo.docks_and_hobos;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }

        String configFilePath = args[0];

        ConfigReader config = ConfigReader.getInstance();
        config.readConfig(configFilePath);

        Tunnel tunnel = new Tunnel();
        ShipGenerator shipGenerator = new ShipGenerator(tunnel);
        Dock dock = new Dock(tunnel);
        HoboGroup hobos = new HoboGroup(dock);

        Thread shipGeneratorThread = new Thread(shipGenerator);
        Thread dockThread = new Thread(dock);
        Thread hobosThread = new Thread(hobos);

        shipGeneratorThread.start();
        dockThread.start();
        hobosThread.start();

        try {
            shipGeneratorThread.join();
            dockThread.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}