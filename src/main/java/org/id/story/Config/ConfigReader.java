package org.id.story.Config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class ConfigReader {

    private final JSONObject config;

    public ConfigReader(String configFile) {
        this.config = readConfig(configFile);
    }
    private JSONObject readConfig(String configFile) {
        JSONParser parser = new JSONParser();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            if (inputStream == null) {
                System.err.println("Configuration file not found: " + configFile);
                return null;
            }

            Object obj = parser.parse(reader);
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            System.err.println("Error reading/parsing configuration file: " + e.getMessage());
            return null;
        }
    }

    public String getStringProperty(String key) {
        return config.containsKey(key) ? (String) config.get(key) : "";
    }

    public Long getLongProperty(String key) {
        return config.containsKey(key) ? (Long) config.get(key) : 10000;
    }

    public List<String> getListProperty(String key) {
        return config.containsKey(key) ? (List<String>) config.get(key) : Collections.emptyList();
    }
}
