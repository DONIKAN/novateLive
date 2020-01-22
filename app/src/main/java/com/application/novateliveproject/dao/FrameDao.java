package com.application.novateliveproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.application.novateliveproject.entity.FrameEntity;

import java.util.List;

@Dao
public interface FrameDao {

    @Insert
    void createFrame(FrameEntity frameEntity);

    @Query("DELETE FROM frame_table")
    void deleteAll();

    @Query("SELECT * from frame_table")
    LiveData<List<FrameEntity>> getFrames();
}
