package org.geektimes.configuration.microprofile.configsource.servlet;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author ajin
 */

public class ServletContextConfigInitializer implements ServletContextListener {

    private static final String MICROPROFILE_CONFIG = "microProfileConfig";

    private static ServletContext servletContext;

    static ServletContext getServletContext() {
        if (null == servletContext) {
            throw new IllegalStateException("获取ServletContext异常");
        }
        return servletContext;
    }

    public static final ThreadLocal<Config> CONFIG_HOLDER = ThreadLocal.withInitial(() -> null);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContextConfigInitializer.servletContext = servletContextEvent.getServletContext();
        ServletContext             servletContext             = ServletContextConfigInitializer.servletContext;
        ServletContextConfigSource servletContextConfigSource = new ServletContextConfigSource(servletContext);

        // 获取当前ClassLoader
        ClassLoader classLoader = servletContext.getClassLoader();

        // 通过java spi机制加载并获取 DefaultConfigProviderResolver实例
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        ConfigBuilder          configBuilder          = configProviderResolver.getBuilder();
        // 配置ClassLoader
        configBuilder.forClassLoader(classLoader);
        // 默认配置源（内建的，静态的）
        configBuilder.addDefaultSources();
        //  通过发现配置源（动态的） SPI机制
        configBuilder.addDiscoveredSources();

        // 增加扩展配置源（基于Servlet引擎）
        configBuilder.withSources(servletContextConfigSource);

        configBuilder.addDiscoveredConverters();

        // 获取Config
        Config config = configBuilder.build();
        // 注册Config关联到当前ClassLoader
        configProviderResolver.registerConfig(config, classLoader);

        String applicationName = config.getValue("application.name", String.class);
        System.out.println(applicationName);
        servletContext.setAttribute(MICROPROFILE_CONFIG, config);
        CONFIG_HOLDER.set(config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
