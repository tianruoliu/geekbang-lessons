package org.geektimes.configuration.microprofile.config.servlet.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * {@link Config} {@link ServletRequestListener}
 *
 * @author ajin
 */

public class ConfigServletRequestListener implements ServletRequestListener {

    private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {


        ServletContext         servletContext         = servletRequestEvent.getServletContext();
        ClassLoader            classLoader            = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config                 config                 = configProviderResolver.getConfig(classLoader);

        configThreadLocal.set(config);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        configThreadLocal.remove();
    }

}
