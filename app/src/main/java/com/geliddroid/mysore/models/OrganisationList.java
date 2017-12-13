package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 27-11-2017.
 */

public class OrganisationList {
    private String message;

    @SerializedName("organisation")
    private ArrayList<Organisation> organisation;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Organisation> getOrganisation() {
        return organisation;
    }

    public void setOrganisation(ArrayList<Organisation> organisation) {
        this.organisation = organisation;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
