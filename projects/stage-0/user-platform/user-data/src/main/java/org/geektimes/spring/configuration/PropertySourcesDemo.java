package org.geektimes.spring.configuration;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import static java.lang.System.out;

/**
 * @author ajin
 */

public class PropertySourcesDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment        environment        = applicationContext.getEnvironment();
        MutablePropertySources         propertySources    = environment.getPropertySources();
        propertySources.stream().forEach(out::println);
        applicationContext.refresh();
        applicationContext.close();
    }
}
