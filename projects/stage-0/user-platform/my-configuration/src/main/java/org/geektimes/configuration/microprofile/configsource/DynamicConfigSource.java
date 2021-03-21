package org.geektimes.configuration.microprofile.configsource;

import java.util.Map;

/**
 * 动态配置源
 *
 * @author ajin
 */

public class DynamicConfigSource extends MapBasedConfigSource {

    private Map configData;

    public DynamicConfigSource() {
        super("DynamicConfigSource", 500);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        this.configData = configData;
    }

    /**
     * 异步 更新
     */
    public void onUpdate(String data) {

    }
}
