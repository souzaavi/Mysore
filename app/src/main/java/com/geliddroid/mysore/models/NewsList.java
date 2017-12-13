package com.geliddroid.mysore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by levin on 12-11-2017.
 */

public class NewsList {
    private String message;

    @SerializedName("news")
    private List<News> newsList;

    private String success;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }


    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
