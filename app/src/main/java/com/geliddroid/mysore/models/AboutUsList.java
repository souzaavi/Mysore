package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by levin on 12-11-2017.
 */

public class AboutUsList {
    private String message;

    @SerializedName("aboutus")
    private List<AboutUs> AboutList;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AboutUs> getAboutList() {
        return AboutList;
    }

    public void setAboutList(List<AboutUs> aboutList) {
        AboutList = aboutList;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
