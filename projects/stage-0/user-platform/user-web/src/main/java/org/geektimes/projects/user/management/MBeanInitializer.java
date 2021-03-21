package org.geektimes.projects.user.management;

import org.geektimes.projects.user.domain.User;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.management.ManagementFactory;

public class MBeanInitializer
        extends HttpServlet
        /* implements ServletContextListener*/ {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            registerUserMBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUserMBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = null;
        try {
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
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(user);
        }
    }


    private static Object createUserMBean(User user) {
        return new UserManager(user);
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
