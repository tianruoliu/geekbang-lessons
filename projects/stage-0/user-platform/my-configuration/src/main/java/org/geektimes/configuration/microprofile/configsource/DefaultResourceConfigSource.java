package org.geektimes.configuration.microprofile.configsource;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author ajin
 */

public class DefaultResourceConfigSource extends MapBasedConfigSource {

    private static final String configFileLocation = "META-INF/microprofile-config.properties";

    private static final Logger logger = Logger.getLogger(
        "org.geektimes.configuration.microprofile.configsource.DefaultResourceConfigSource");

    public DefaultResourceConfigSource() {
        super("Default Config File", 100);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        ClassLoader classLoader = getClass().getClassLoader();
        URL         url         = classLoader.getResource(configFileLocation);
        if (url == null) {
            logger.info("The default config file can't be found in the classpath : " + configFileLocation);
            return;
        }
        try (InputStream inputStream = classLoader.getResourceAsStream(configFileLocation)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            configData.putAll(properties);
        }
    }
}
