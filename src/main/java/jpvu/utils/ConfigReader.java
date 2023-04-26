package jpvu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import static jdk.xml.internal.SecuritySupport.getResourceAsStream;

public class ConfigReader {

    private static String propertiesPath = "configs/env_config.properties";
    private static Properties config = new Properties();

    public static String getConfig(String property) {
        try(InputStream ip = ConfigReader.class.getClassLoader().getResourceAsStream(propertiesPath)){
            config.load(ip);
        }catch(IOException e){
            e.printStackTrace();
        }

        return config.getProperty(property);
    }
}
