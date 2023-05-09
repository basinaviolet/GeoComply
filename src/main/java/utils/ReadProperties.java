package utils;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    private static Properties properties;
    private static final String filename = "config.properties";
    protected static final Logger logger = (Logger) LogManager.getLogger(BasePage.class.getName());

    static {
        properties = new Properties();
        try {
            properties.load(ReadProperties.class.getClassLoader().getResourceAsStream(filename));
            logger.debug("Read config properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }
    public static String getEmail() {
        return properties.getProperty("email");
    }
    public static String getPassword() {
        return properties.getProperty("password");
    }
    public static String getPasswordGmail() {
        return properties.getProperty("passwordGmail");
    }
}
