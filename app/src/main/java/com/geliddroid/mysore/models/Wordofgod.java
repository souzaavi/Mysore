package com.geliddroid.mysore.models;

import java.io.Serializable;

/**
 * Created by levin on 29-11-2017.
 */

public class Wordofgod implements Serializable{
    private String id;

    private String reading;

    private String title;

    private String verse;

    private String reading_type;

    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getReading_type() {
        return reading_type;
    }

    public void setReading_type(String reading_type) {
        this.reading_type = reading_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
