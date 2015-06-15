package org.wildfly.examples.shutdown;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless(name="HelloBean")
@Local(SleepingHello.class)
public class SleepingHelloBean implements SleepingHello {

	public String getHello(int sleepTime) {

			for(int i=0;i<100;i++){
				try {
				Thread.sleep(sleepTime*1000);
				} catch(Exception ignored){}
				System.out.println("ZZZ");
			}

		System.out.println("END");
		return "Hello";
	}

	public void sleep(){
		try {
//			System.out.println("ZZZ ZE SLEEPA");
//			new Exception().printStackTrace();
			Thread.sleep(1000);
		} catch(Exception ignored){}
	}

}
