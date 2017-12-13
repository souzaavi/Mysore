package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 07-11-2017.
 */

public class NotificationList {
    private String message;

    @SerializedName("notification")
    private ArrayList<Notification> notification;
    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
