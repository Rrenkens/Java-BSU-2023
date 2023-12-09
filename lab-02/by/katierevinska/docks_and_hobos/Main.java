package by.katierevinska.docks_and_hobos;

import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        Process.getInstance().createObjects();
        System.out.println("process start");
        Process.getInstance().startProcess();
    }


}
