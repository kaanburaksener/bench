package com.kaanburaksener.bench.core;

/**
 * Created by kaanburaksener on 02/04/16.
 */
public class Request {
    private int id;
    private String title;
    private String description;
    private String location;
    private String playerPosition;
    private String time;
    private int statusID;
    private String status;
    private int ownerID;
    private String ownerName;

    public Request(int id, String title, String description, String location, String playerPosition, String time, int statusID, String status, int ownerID, String ownerName){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.playerPosition = playerPosition;
        this.time = time;
        this.statusID = statusID;
        this.status = status;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
