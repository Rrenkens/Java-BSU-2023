package by.katierevinska.docks_and_hobos;

import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        Controller.getInstance().createObjects();
        System.out.println("Controller start");
        Controller.getInstance().startProcess();
    }


}
