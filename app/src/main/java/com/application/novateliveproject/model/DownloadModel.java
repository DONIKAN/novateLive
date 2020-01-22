package com.application.novateliveproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "download_table")
public class DownloadModel {
    public String imagePath;
    public int framePosition;
    public int valuePosition;
    public Long downloadId;
    public int Downloaded;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDownloaded() {
        return Downloaded;
    }

    public void setDownloaded(int downloaded) {
        Downloaded = downloaded;
    }

    public Long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }

    public int getFramePosition() {
        return framePosition;
    }

    public void setFramePosition(int framePosition) {
        this.framePosition = framePosition;
    }

    public int getValuePosition() {
        return valuePosition;
    }

    public void setValuePosition(int valuePosition) {
        this.valuePosition = valuePosition;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getframePosition() {
        return framePosition;
    }

    public void setframePosition(int framePosition) {
        this.framePosition = framePosition;
    }
}
