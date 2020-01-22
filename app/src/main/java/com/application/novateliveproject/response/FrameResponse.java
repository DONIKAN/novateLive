package com.application.novateliveproject.response;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.application.novateliveproject.entity.DataConverter;
import com.application.novateliveproject.model.FrameData;

import java.util.List;

@Entity(tableName = "frame_response_table")
public class FrameResponse {

    /**
     * frame_height : 1000
     * frame_width : 1600
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FrameData> getData() {
        return data;
    }

    public void setData(List<FrameData> data) {
        this.data = data;
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
}
