package org.geektimes.projects.user.management;

import org.geektimes.projects.user.domain.User;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * Servlet容器启动时注册MBean
 *
 * @author ajin
 */
public class MBeanInitializer implements ServletContextListener {

    private void registerUserMBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = null;
        try {
            // http://127.0.0.1:8080/jolokia/read/jolokia:name=User
            objectName = new ObjectName("jolokia:name=User");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

        // 创建 UserMBean 实例
        User user = new User();
        try {
            mBeanServer.registerMBean(createUserMBean(user), objectName);
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        }
    }

    private static Object createUserMBean(User user) {
        return new UserManager(user);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            registerUserMBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    //    @Override
    //    public void contextInitialized(ServletContextEvent sce) {
    //        try {
    //            registerUserMBean();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    @Override
    //    public void contextDestroyed(ServletContextEvent sce) {
    //
    //    }
}
