package org.geektimes.context.core;

import java.util.List;

/**
 * 组件上下文
 *
 * @author ajin
 */
public interface ComponentContext {
    // 生命周期方法

    /**
     * 上下文初始化
     */
    void init();

    /**
     * 上下文销毁
     */
    void destroy();

    // 组件操作方法

    /**
     * 通过名称查找组件
     *
     * @param name 组件名称
     * @param <C>  组件对象类型
     * @return 组件 ，如果找不到，则返回null
     */
    <C> C getComponent(String name);

    /**
     * 获取所有的组件名称
     */
    List<String> getComponentNames();

}
