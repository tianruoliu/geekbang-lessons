package org.geektimes.configuration.microprofile.config.configsource.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * 绕过web.xml文件去 注册ServletContextListener
 *
 * @author ajin
 * @see ServletContainerInitializer
 */

public class ServletConfigInitializer implements ServletContainerInitializer {

    /**
     *  Interface which allows a library/runtime to be notified of a web
     *  * application's startup phase and perform any required programmatic
     *  * registration of servlets, filters, and listeners in response to it.
     * */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        // 增加 ServletContextListener
        servletContext.addListener(ServletContextConfigInitializer.class);
    }
}
