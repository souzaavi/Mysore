package com.geliddroid.mysore.models;

import java.io.Serializable;

/**
 * Created by levin on 27-11-2017.
 */

public class OrgData implements Serializable {
    private String id;

    private String description;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
