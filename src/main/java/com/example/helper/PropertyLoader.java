package com.example.helper;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find app.properties");
            }
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load properties file.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
