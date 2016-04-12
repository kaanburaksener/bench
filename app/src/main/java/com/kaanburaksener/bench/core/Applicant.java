package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 12/04/16.
 */
public class Applicant {
    private int userID;
    private String name;
    private String location;
    private String birthday;
    private int applicationStatusID;

    public Applicant(){}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        String notAvailable = "-";

        if(location != null || location != "null") {
            return location;
        } else {
            return notAvailable;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        String notAvailable = "-";

        if(birthday != null || birthday != "null") {
            return birthday;
        } else {
            return notAvailable;
        }
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getApplicationStatusID() {
        return applicationStatusID;
    }

    public void setApplicationStatusID(int applicationStatusID) {
        this.applicationStatusID = applicationStatusID;
    }
}
