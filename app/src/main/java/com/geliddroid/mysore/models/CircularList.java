package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 28-11-2017.
 */

public class CircularList {
    private String message;

    @SerializedName("circular")
    private ArrayList<Circular> circular;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Circular> getCircular() {
        return circular;
    }

    public void setCircular(ArrayList<Circular> circular) {
        this.circular = circular;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
