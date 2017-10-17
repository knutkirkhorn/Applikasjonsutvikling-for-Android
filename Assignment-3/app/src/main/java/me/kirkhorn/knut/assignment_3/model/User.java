package me.kirkhorn.knut.assignment_3.model;

/**
 * Created by Knut on 16.10.2017.
 */

public class User {
    private String name;
    private String birthday;

    public User(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return name + " (" + birthday + ")";
    }
}
