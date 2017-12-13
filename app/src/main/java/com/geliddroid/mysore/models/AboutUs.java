package com.geliddroid.mysore.models;

import java.io.Serializable;

/**
 * Created by levin on 12-11-2017.
 */

public class AboutUs implements Serializable {
    private String content;

    private String id;

    private String title;

    private String updated_date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }
}
