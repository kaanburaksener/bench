package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 02/04/16.
 */
public class Request_Application {
    private int requestID;
    private int applicantID;
    private String applicantName;
    private int applicationStatusID;
    private String applicationStatus;

    public Request_Application(int requestID, int applicantID, String applicantName, int applicationStatusID, String applicationStatus){
        this.requestID = requestID;
        this.applicantID = applicantID;
        this.applicantName = applicantName;
        this.applicationStatusID = applicationStatusID;
        this.applicationStatus = applicationStatus;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getApplicationStatusID() {
        return applicationStatusID;
    }

    public void setApplicationStatusID(int applicationStatusID) {
        this.applicationStatusID = applicationStatusID;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
