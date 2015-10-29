package org.tadamski.examples.js;

import javax.inject.Named;

@Named("abean")
public class ABean {
    public String sayHello(){
        return "Hello from ABean!";
    }
}
