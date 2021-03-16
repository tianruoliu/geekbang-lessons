//package org.geektimes.jmx;
//
//import com.sun.jdmk.comm.HtmlAdaptorServer;
//
//import javax.management.*;
//import java.lang.management.ManagementFactory;
//
//public class HelloAgent {
//
//    public static void main(String[] args) throws JMException, InterruptedException {
//        // 通过ManagementFactory获取MBeanServer(用作MBean容器)
//        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//
//        // ObjectName中的取名是有一定规范的，格式为：“域名：name=MBean名称”，其中域名和MBean的名称可以任意取
//        // 作用：唯一标识MBean
//        ObjectName helloName = new ObjectName("jmxBean:name=hello");
//        server.registerMBean(new Hello(), helloName);
//
////        Thread.sleep(60 * 60 * 1000);
//
//        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//        HtmlAdaptorServer adaptor = new HtmlAdaptorServer();
//
//        server.registerMBean(adaptor, adapterName);
//
//        adaptor.start();
//
//        Thread.sleep(60 * 60 * 1000);
//    }
//}
