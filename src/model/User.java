package model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return this.name + "\t年龄：" + this.age + "\t电话：" + this.tel;
    }
}
