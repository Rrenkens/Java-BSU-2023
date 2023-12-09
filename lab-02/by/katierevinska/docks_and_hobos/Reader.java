package by.katierevinska.docks_and_hobos;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Reader {
    String fileName;
    Reader(String fileName){
        this.fileName = fileName;
    }
     JSONObject getJsonObject() throws IOException, ParseException {
        Object object = new JSONParser().parse(new FileReader(fileName));
        return (JSONObject) object;
    }
}
