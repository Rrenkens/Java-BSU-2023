package by.Dzenia.docks_and_hobos.Controller;
import by.Dzenia.docks_and_hobos.Persons.Cargo;
import by.Dzenia.docks_and_hobos.Persons.Tunnel;
import by.Dzenia.docks_and_hobos.RunnableObjects.Dock;
import by.Dzenia.docks_and_hobos.RunnableObjects.Hobos;
import by.Dzenia.docks_and_hobos.RunnableObjects.ShipGenerator;
import java.util.ArrayList;

public class Model {
    private final Tunnel tunnel;
    private final Hobos hobos;
    private final ShipGenerator shipGenerator;
    private final ArrayList<Cargo> cargos;
    private final ArrayList<Dock> docks;

    public Model(Tunnel tunnel, Hobos hobos, ShipGenerator shipGenerator, ArrayList<Cargo> cargos, ArrayList<Dock> docks) {
        this.tunnel = tunnel;
        this.hobos = hobos;
        this.shipGenerator = shipGenerator;
        this.cargos = cargos;
        this.docks = docks;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public Hobos getHobos() {
        return hobos;
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    public ArrayList<Cargo> getCargos() {
        return cargos;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }
}
