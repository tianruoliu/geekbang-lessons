package org.geektimes.context.servlet;

import org.geektimes.context.ComponentContextInitializerListener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * 不借助web.xml注册 {@link ServletContextListener}
 *
 * @author ajin
 */

public class ComponentContextInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        servletContext.addListener(ComponentContextInitializerListener.class);
    }
}
