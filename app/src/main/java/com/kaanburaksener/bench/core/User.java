package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 26/03/16.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String location;
    private String birthday;
    private String createdAt;

    public User(int id, String name, String email, String password, String location, String birthday, String createdAt){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.birthday = birthday;
        this.createdAt = createdAt;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
