package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 02/04/16.
 */

public class RequestApplication {
    private int applicantID;
    private int applicationStatusID;
    private int ownerID;
    private int requestID;
    private String applicationStatus;
    private String title;
    private String location;
    private String playerPosition;
    private String time;
    private String requestStatus;
    private String ownerName;

    public RequestApplication(){}

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getApplicationStatusID() {
        return applicationStatusID;
    }

    public void setApplicationStatusID(int applicationStatusID) {
        this.applicationStatusID = applicationStatusID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatusName) {
        this.applicationStatus = applicationStatusName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
