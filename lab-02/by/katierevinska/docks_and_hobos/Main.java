package by.katierevinska.docks_and_hobos;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.Thread;


public class Main {
    public static void main(String[] args) throws IOException, ParseException, JSONException {
        Process process = new Process();
        process.createObjects("config.json");
        System.out.println("process start");
        process.startProcess();
    }


}
