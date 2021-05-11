package org.geektimes.projects.user.mybatis.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活MyBatis
 *
 * @author ajin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MyBatisBeanDefinitionRegistrar.class)
public @interface EnableMyBatis {
    /**
     * @return the bean name of {@link SqlSessionFactoryBean}
     */
    String value() default "sqlSessionFactoryBean";

    /**
     * @return DataSource Bean name
     */
    String dataSource();

    /**
     * @return the location of {@link Configuration}
     */
    String configLocation();

    /**
     * @return the location of {@link Mapper}
     * @see MapperScan
     */
    String[] mapperLocations() default {};

    String environment() default "SqlSessionFactoryBean";

    String typeAliasesPackage() default "";

    String transactionFactory();

    String databaseIdProvider();

    String objectFactory();


    //<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    //   <property name="dataSource" ref="dataSource" />
    //   <property name="configuration">
    //     <bean class="org.apache.ibatis.session.Configuration">
    //       <property name="mapUnderscoreToCamelCase" value="true"/>
    //     </bean>
    //   </property>
    // </bean>
   String configuration();
}
