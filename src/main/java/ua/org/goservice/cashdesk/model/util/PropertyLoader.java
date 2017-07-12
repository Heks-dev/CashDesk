package ua.org.goservice.cashdesk.model.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private final Properties properties = new Properties();

    public PropertyLoader(String location) {
        load(location);
    }

    private void load(String location) {
        try {
            properties.load(getClass().getResourceAsStream(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
