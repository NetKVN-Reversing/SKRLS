package com.skrls.cli;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    private Properties properties;

    private PropertiesHandler(Properties properties){
        this.properties = properties;
    }

    public String getProperty(String prop){
        return properties.getProperty(prop);
    }

    public static PropertiesHandler fromFile(String pathToFile) throws IOException{
        Properties props;
        try {
            InputStream file = new FileInputStream(pathToFile);
            props = new Properties();
            props.load(file);
        }catch(IOException e) {
            throw e;
        }
        return new PropertiesHandler(props);
    }

}
