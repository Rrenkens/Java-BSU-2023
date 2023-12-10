import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Generator implements Runnable{

    JSONObject jo;
    static int generate(long number) {
        return (int) (Math.random() * number);
    }
    Generator(JSONObject jo) { this.jo = jo; }

    @Override
    public void run() {
        long generating_time = (long) ((JSONObject) jo.get("generator")).get("generating_time");
        JSONArray ships = (JSONArray) jo.get("ships");
        for (Object o : ships) {
            JSONObject ship = (JSONObject) o;
            Object cargo_type = ship.get("cargo_type");
            if (cargo_type.getClass() == String.class) {
                Main.ships.add(new Ship((long) ship.get("capacity"), (String) cargo_type));
            } else {
                Main.ships.add(new Ship((long) ship.get("capacity"), (long) cargo_type));
            }
            try {
                Thread.sleep(generating_time);
            } catch (InterruptedException e) {
                System.out.println("Generator stopped");
                Thread.currentThread().interrupt();
            }
        }
    }
}
