package com.exam.myapp.entity;

import java.io.Serializable;

public class People implements Serializable {
    public int age;
    public String name;
    private transient String sex;// transient修饰的字段不被序列化

    private static final long serialVersionUID = 234444L;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
