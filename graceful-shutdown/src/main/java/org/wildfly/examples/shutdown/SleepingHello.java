package org.wildfly.examples.shutdown;

import javax.ejb.Local;

@Local
public interface SleepingHello {
	String getHello(int sleepTime);
	void sleep();
}
