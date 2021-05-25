package org.apache.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 简单测试
 * @author ajin
 */
@SpringBootApplication
public class Boot  {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =SpringApplication.run(Boot.class,args);

        SqlSessionFactory bean = applicationContext.getBeanFactory().getBean(SqlSessionFactory.class);
        System.out.println(bean);
    }
}
