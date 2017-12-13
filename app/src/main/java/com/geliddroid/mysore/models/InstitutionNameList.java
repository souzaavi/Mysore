package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 25-11-2017.
 */

public class InstitutionNameList {
    private String message;

    private String success;

    @SerializedName("institution")
    private ArrayList<InstitutionName> institutionNames;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<InstitutionName> getInstitutionNames() {
        return institutionNames;
    }

    public void setInstitutionNames(ArrayList<InstitutionName> institutionNames) {
        this.institutionNames = institutionNames;
    }
}
