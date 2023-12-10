package by.waitingsolong.docks_and_hobos.helpers;

import org.json.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class Config {
    private JSONObject config;

    public Config(String path) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        this.config = new JSONObject(content);
    }

    public <T> T get(String key) throws JSONException {
        if (!this.config.has(key)) {
            throw new IllegalArgumentException("No such key in config.json: " + key);
        }
        Object value = this.config.get(key);
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            ArrayList<Object> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                list.add(array.get(i));
            }
            return (T) list;
        }
        return (T) value;
    }


}
