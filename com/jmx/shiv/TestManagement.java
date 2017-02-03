package com.jmx.shiv;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class TestManagement {
	private static final String DEFAULT_NAME = "Shiv";
	private static final int DEFAULT_AGE = 28;

	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, InterruptedException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		TestInterface mBean = new TestInterface(DEFAULT_AGE, DEFAULT_NAME);
        ObjectName name = new ObjectName("com.jmx.shiv.jmx:type=TestInterface");
        mbs.registerMBean(mBean, name);
        do{
            Thread.sleep(3000);
            System.out.println("My Name="+mBean.getName()+":::Age is="+mBean.getAge());
        }while(mBean.getAge() !=0);  // If we will set age to 0 from JMX, program will stop.
	}
}
