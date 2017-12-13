package com.geliddroid.mysore.models;

/**
 * Created by levin on 08-11-2017.
 */

public class Mass {
    private String id;

    private String updated_date;

    private String address;

    private String name;

    private String suntiming;

    private String adoration;

    private String timing;

    private String contact;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getSuntiming() {
        return suntiming;
    }

    public void setSuntiming(String suntiming) {
        this.suntiming = suntiming;
    }

    public String getAdoration() {
        return adoration;
    }

    public void setAdoration(String adoration) {
        this.adoration = adoration;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
