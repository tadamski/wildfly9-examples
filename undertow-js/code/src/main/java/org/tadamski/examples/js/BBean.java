package org.tadamski.examples.js;


import javax.inject.Named;
import javax.transaction.SystemException;

@Named(value = "bbean")
public class BBean {

    public String sayHello() throws SystemException{
        return "Hey it is BBean";
    }

}
