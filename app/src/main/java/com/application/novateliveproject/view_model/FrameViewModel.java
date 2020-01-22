package com.application.novateliveproject.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.repository.FrameRepository;

import java.util.List;

public class FrameViewModel extends AndroidViewModel {
    private FrameRepository repository;
    private LiveData<List<FrameEntity>>mFrame;


    public FrameViewModel(@NonNull Application application) {
        super(application);
        repository=new FrameRepository(application);
        mFrame=repository.getFrameData();
    }
    public LiveData<List<FrameEntity>>getFrameData(){
        return mFrame;
    }
    public void insert(FrameEntity entity){
        repository.insertFrame(entity);
    }
}
