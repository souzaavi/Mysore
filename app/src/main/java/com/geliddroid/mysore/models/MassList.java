package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by levin on 08-11-2017.
 */

public class MassList {
    private String message;

    @SerializedName("mass")
    private List<Mass> massList;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Mass> getMassList() {
        return massList;
    }

    public void setMassList(List<Mass> massList) {
        this.massList = massList;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
