package org.geektimes.configuration.microprofile.configsource.servlet;

import org.geektimes.configuration.microprofile.configsource.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * {@link ServletContext}配置源
 *
 * @author ajin
 */

public class ServletContextConfigSource extends MapBasedConfigSource {

    private ServletContext servletContext;

    public ServletContextConfigSource(ServletContext servletContext) {
        super("ServletContext Init Parameters", 500);
        // this.servletContext = servletContext;
    }

    private void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        if (servletContext == null) {
            this.servletContext = ServletContextConfigInitializer.getServletContext();
        }

        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String parameterName  = initParameterNames.nextElement();
            String parameterValue = servletContext.getInitParameter(parameterName);
            configData.put(parameterName, parameterValue);
        }
    }
}
