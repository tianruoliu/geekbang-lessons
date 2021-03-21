package org.geektimes.configuration.microprofile.configsource;

import java.util.Map;
import java.util.Set;

/**
 * Java系统属性配置源
 * <p>
 * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整  ，-Dapplication.name=user-web
 *
 * @author ajin
 */

public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {

    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties", 400);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        configData.putAll(System.getProperties());
    }

}
