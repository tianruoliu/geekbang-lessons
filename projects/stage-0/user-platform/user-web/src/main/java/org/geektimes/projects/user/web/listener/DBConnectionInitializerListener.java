package org.geektimes.projects.user.web.listener;

import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Servlet容器启动时，触发监听，加载数据库连接
 */
@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        DBConnectionManager connectionManager = new DBConnectionManager();
//        String databaseURL = "jdbc:derby:/db/user-platform;create=true";
//        try {
//            Connection connection = DriverManager.getConnection(databaseURL);
//            connectionManager.setConnection(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
