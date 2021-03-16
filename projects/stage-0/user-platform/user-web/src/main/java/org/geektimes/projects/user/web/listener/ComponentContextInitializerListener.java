package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * {@link ComponentContext} 初始化监听器{@link ServletContextListener}
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComponentContext componentContext = ComponentContext.getInstance();
        componentContext.destroy();
    }
}
