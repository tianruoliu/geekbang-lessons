package org.geektimes.configuration.file;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 基于文件系统为 Spring Cloud 提供 PropertySourceLocator
 * 实现
 *
 * @author ajin
 */

// @Component
public class FilePropertySourceLocator implements PropertySourceLocator {

    private static final String DEFAULT_PROPERTY_LOCATION = "classpath:META-INF/config/default.properties";

    @Override
    public PropertySource<?> locate(Environment environment) {
        ResourceLoader resourceLoader   = new DefaultResourceLoader();
        Resource       propertyResource = resourceLoader.getResource(DEFAULT_PROPERTY_LOCATION);
        if (propertyResource != null) {
            try {
                // InputStream inputStream = null;
                //
                // inputStream = propertyResource.getInputStream();
                //
                // Properties properties = new Properties();
                //
                // properties.load(inputStream);

                // return new MapPropertySource("file", new HashMap<String, Object>(properties));
                return new ResourcePropertySource("file", propertyResource);

            } catch (IOException e) {
                throw new RuntimeException("locate file :classpath:META-INF/config/default.properties  fail!", e);
            }
        }

        return null;
    }
}
