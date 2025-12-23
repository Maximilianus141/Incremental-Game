package dev.cowycorn.incrgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config instance;
    private Properties properties;

    private Config() {
        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if (in == null) {
                System.out.println("Config file not found!");
                return;
            }
            properties.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Config get(){
        if (instance == null){
            instance = new Config();
        }
        return instance;
    }

    public String get(String key){
        return properties.getProperty(key);
    }

}
