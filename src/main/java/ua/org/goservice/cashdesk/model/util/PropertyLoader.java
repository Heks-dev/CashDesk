package ua.org.goservice.cashdesk.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class PropertyLoader {

    private final Properties properties = new Properties();

    public PropertyLoader(String location) {
        load(location);
    }

    private void load(String location) {
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(location), "UTF-8")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
