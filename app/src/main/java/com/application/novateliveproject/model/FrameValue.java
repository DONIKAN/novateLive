package com.application.novateliveproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class FrameValue {

    /**
     * type : image
     * url : http:
     */

    private String type;
    private int size=1590000;
    private String url;
    private long duration = 3000;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
