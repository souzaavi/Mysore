package com.geliddroid.mysore.models;

import java.io.Serializable;

/**
 * Created by levin on 27-11-2017.
 */

public class Organisation implements Serializable {
    private String id;

    private String updated_date;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
