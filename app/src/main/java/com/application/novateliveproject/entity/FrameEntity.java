package com.application.novateliveproject.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.application.novateliveproject.model.FrameData;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "frame_table")
public class FrameEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String frame_height;
    private String frame_width;
    @TypeConverters({DataConverter.class})
    private List<FrameData> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrame_height() {
        return frame_height;
    }

    public void setFrame_height(String frame_height) {
        this.frame_height = frame_height;
    }

    public String getFrame_width() {
        return frame_width;
    }

    public void setFrame_width(String frame_width) {
        this.frame_width = frame_width;
    }

    public List<FrameData> getData() {
        return data;
    }

    public void setData(List<FrameData> data) {
        this.data = data;
    }
}
