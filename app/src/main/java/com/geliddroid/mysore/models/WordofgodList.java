package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by levin on 29-11-2017.
 */

public class WordofgodList {
    private String message;

    @SerializedName("wordofgod")
    private ArrayList<Wordofgod> wordofgod;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Wordofgod> getWordofgod() {
        return wordofgod;
    }

    public void setWordofgod(ArrayList<Wordofgod> wordofgod) {
        this.wordofgod = wordofgod;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
