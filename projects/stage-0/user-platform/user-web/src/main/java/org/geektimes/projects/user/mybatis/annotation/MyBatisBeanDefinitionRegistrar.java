package org.geektimes.projects.user.mybatis.annotation;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * {@link ImportBeanDefinitionRegistrar}实现MyBatis组件的装配
 *
 * @author ajin
 */

public class MyBatisBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(
            SqlSessionFactoryBean.class);
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableMyBatis.class.getName());
        /**
         *  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
         *   <property name="dataSource" ref="dataSource" />
         *   <property name="mapperLocations" value="classpath*:" />
         *  </bean >
         */

        beanDefinitionBuilder.addPropertyReference("dataSource", (String)attributes.get("dataSource"));
        // Spring String 类型可以自动转化 Spring Resource
        beanDefinitionBuilder.addPropertyValue("configLocation", attributes.get("configLocation"));
        beanDefinitionBuilder.addPropertyValue("mapperLocations", attributes.get("mapperLocations"));
        beanDefinitionBuilder.addPropertyValue("environment", resolvePlaceHolder(attributes.get("environment")));

        // 完善
        beanDefinitionBuilder.addPropertyValue("typeAliasesPackage", attributes.get("typeAliasesPackage"));


        beanDefinitionBuilder.addPropertyReference("transactionFactory", (String)attributes.get("transactionFactory"));
        beanDefinitionBuilder.addPropertyReference("databaseIdProvider", (String)attributes.get("databaseIdProvider"));
        beanDefinitionBuilder.addPropertyReference("objectFactory", (String)attributes.get("objectFactory"));
        beanDefinitionBuilder.addPropertyReference("cache", (String)attributes.get("cache"));


        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        registry.registerBeanDefinition((String)attributes.get("value"), beanDefinition);

    }

    private Object resolvePlaceHolder(Object value) {
        if (value instanceof String) {
            return environment.resolvePlaceholders((String)value);
        }
        return value;
    }
}
