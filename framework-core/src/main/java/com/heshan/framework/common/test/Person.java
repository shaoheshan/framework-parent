package com.heshan.framework.common.test;

import java.io.Serializable;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2016/5/27 17:37
 */
public   class  Person  implements Serializable {

    private static final long serialVersionUID = -3562550857760039655L;

    private String  name ;

    private int age ;

    public Person(){}

    public Person(String name,  int age) {

        super ();

        this . name = name;

        this . age = age;

    }

    public String getName() {

        return name ;

    }

    public void setName(String name) {

        this . name = name;

    }

    public int getAge() {

        return age ;

    }

    public void setAge( int age) {

        this . age = age;

    }

    @Override

    public String toString() {

        return "Person [name=" +  name +  ", age=" +  age +  "]" ;

    }

}
