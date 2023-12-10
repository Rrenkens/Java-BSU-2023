package by.Bvr_Julia.docks_and_hobos;

public class Ship {
    private final Long capacity;
    private final String cargo;
    private volatile Long amount;

    Ship(Long capacity, String cargo) {
        this.capacity = capacity;
        this.cargo = cargo;
        amount = capacity;
        System.out.println("Ship was made up. " + cargo + Long.toString((capacity)));
    }

    public Long getCurrentAmount() {
        return amount;
    }

    public Long getSomeCargo(Long amount) {
        if (amount.compareTo(this.amount) < 0) {
            this.amount -= amount;
            return amount;
        } else {
            Long tmp = this.amount;
            this.amount = (long) 0;
            System.out.println("Ship is empty. ");
            return tmp;
        }
    }

    public String knowCargo() {
        return cargo;
    }
}
