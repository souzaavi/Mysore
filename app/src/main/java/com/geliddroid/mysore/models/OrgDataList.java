package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 27-11-2017.
 */

public class OrgDataList {
    private String message;

    @SerializedName("data")
    private ArrayList<OrgData> data;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<OrgData> getData() {
        return data;
    }

    public void setData(ArrayList<OrgData> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
