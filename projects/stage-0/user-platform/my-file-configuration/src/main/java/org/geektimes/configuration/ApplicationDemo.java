package org.geektimes.configuration;

import org.geektimes.configuration.file.FilePropertySourceLocator;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * @author ajin
 */
public class ApplicationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FilePropertySourceLocator.class);

        applicationContext.refresh();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        PropertySourceLocator   locator        = applicationContext.getBean(PropertySourceLocator.class);
        PropertySource<?>       propertySource = locator.locate(environment);
        applicationContext.close();

    }

}
