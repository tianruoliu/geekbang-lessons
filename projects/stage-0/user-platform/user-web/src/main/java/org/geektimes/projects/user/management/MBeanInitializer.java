package org.geektimes.projects.user.management;

import org.geektimes.projects.user.domain.User;

import javax.management.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.lang.management.ManagementFactory;

public class MBeanInitializer extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = null;
        try {
            objectName = new ObjectName("jolokia:type=User");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

        // 创建 UserMBean 实例
        User user = new User();
        try {
            mBeanServer.registerMBean(createUserMBean(user), objectName);
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
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
}
