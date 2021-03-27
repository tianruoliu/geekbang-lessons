package org.geektimes.context.servlet.listener;

import org.geektimes.context.core.ClassicComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * {@link ClassicComponentContext} 初始化监听器{@link ServletContextListener}
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext          servletContext          = servletContextEvent.getServletContext();
        ClassicComponentContext classicComponentContext = new ClassicComponentContext();
        classicComponentContext.init(servletContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ClassicComponentContext classicComponentContext = ClassicComponentContext.getInstance();
        classicComponentContext.destroy();
    }
}
