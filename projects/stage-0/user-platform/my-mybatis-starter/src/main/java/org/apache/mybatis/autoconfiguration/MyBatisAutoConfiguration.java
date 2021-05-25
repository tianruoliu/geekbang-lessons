package org.apache.mybatis.autoconfiguration;

import org.apache.mybatis.annotation.EnableMyBatis;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author ajin
 */
@Configuration
@EnableMyBatis(dataSource = "dataSource", configLocation = "classpath*:META-INF/mybatis/mybatis-config.xml", mapperLocations = {
    "classpath*:sample/config/mappers/**/*.xml"}, environment = "development", typeAliasesPackage = "org.geektimes.projects.user.mybatis.alias", transactionFactory = "springManagedTransactionFactory", databaseIdProvider = "databaseIdProvider", objectFactory = "defaultObjectFactory", configuration ="mybatisConfiguration")
@ImportResource(locations = "classpath*:sample/spring-context.xml") // SqlSessionFactoryBean
public class MyBatisAutoConfiguration {
}
