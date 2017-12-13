package com.geliddroid.mysore.models;

import java.io.Serializable;

/**
 * Created by levin on 28-11-2017.
 */

public class Circular implements Serializable {
    private String id;

    private String updated_date;

    private String link;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
