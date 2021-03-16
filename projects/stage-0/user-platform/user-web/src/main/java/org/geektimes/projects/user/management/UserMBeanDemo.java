package org.geektimes.projects.user.management;

import org.geektimes.projects.user.domain.User;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class UserMBeanDemo {

    public static void main(String[] args) throws Exception {

    }

    private static Object createUserMBean(User user) {
        return new UserManager(user);
    }
}
