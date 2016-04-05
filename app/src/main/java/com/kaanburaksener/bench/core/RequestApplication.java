package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 02/04/16.
 */
public class RequestApplication {
    private int requestID;
    private int applicantID;
    private int applicationStatusID;
    private String applicationStatusName;
    private String createdAt;

    public RequestApplication(int requestID, int applicantID, int applicationStatusID, String applicationStatusName, String createdAt){
        this.requestID = requestID;
        this.applicantID = applicantID;
        this.applicationStatusID = applicationStatusID;
        this.applicationStatusName = applicationStatusName;
        this.createdAt = createdAt;
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

    public int getApplicationStatusID() {
        return applicationStatusID;
    }

    public void setApplicationStatusID(int applicationStatusID) {
        this.applicationStatusID = applicationStatusID;
    }

    public String getApplicationStatusName() {
        return applicationStatusName;
    }

    public void setApplicationStatusName(String applicationStatusName) {
        this.applicationStatusName = applicationStatusName;
    }

    public String getCreatedTime() {
        return createdAt;
    }

    public void setCreatedTime(String createdAt) {
        this.createdAt = createdAt;
    }
}
