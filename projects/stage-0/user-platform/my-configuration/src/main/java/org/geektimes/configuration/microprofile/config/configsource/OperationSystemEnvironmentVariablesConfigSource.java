package org.geektimes.configuration.microprofile.config.configsource;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;

/**
 * 操作系统环境变量 {@link ConfigSource}
 *
 * @author ajin
 */

public class OperationSystemEnvironmentVariablesConfigSource extends MapBasedConfigSource {

    public OperationSystemEnvironmentVariablesConfigSource() {
        super("Operation System Environment Variables", 300);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        configData.putAll(System.getenv());
    }
}
