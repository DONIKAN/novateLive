package com.application.novateliveproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.application.novateliveproject.entity.VlaueConverter;

import java.util.ArrayList;
import java.util.List;
@Entity
public class FrameData {

    /**
     * coord_x : 0
     * coord_y : 0
     * height : 400
     * width : 600
     */
    private String coord_x;
    private String coord_y;
    private String height;
    private String width;
    @TypeConverters({VlaueConverter.class})
    private ArrayList<FrameValue> values;

    public ArrayList<FrameValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<FrameValue> values) {
        this.values = values;
    }

    public String getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(String coord_x) {
        this.coord_x = coord_x;
    }

    public String getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(String coord_y) {
        this.coord_y = coord_y;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
