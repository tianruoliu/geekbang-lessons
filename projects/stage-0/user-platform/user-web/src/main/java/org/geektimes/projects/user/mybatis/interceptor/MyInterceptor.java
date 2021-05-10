package org.geektimes.projects.user.mybatis.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.geektimes.projects.user.repository.UserRepository;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;

/**
 * {@link Interceptor}自定义实现
 *
 * @author ajin
 */

public class MyInterceptor implements Interceptor {

    @Resource
    private UserRepository userRepository;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.refresh();

        MyInterceptor              myInterceptor = new MyInterceptor();
        AutowireCapableBeanFactory beanFactory   = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(myInterceptor);

        applicationContext.close();
        return null;
    }
}
