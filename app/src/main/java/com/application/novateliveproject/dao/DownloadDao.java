package com.application.novateliveproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.model.DownloadModel;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface DownloadDao {

    @Insert
    void createFrame(List<DownloadModel> arrayList);

    @Query("DELETE FROM download_table")
    void deleteAll();

    @Query("SELECT * from download_table")
    LiveData<List<DownloadModel>> downlData();
}
