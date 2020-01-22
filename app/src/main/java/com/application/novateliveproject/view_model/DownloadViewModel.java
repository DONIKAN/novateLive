package com.application.novateliveproject.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.model.DownloadModel;
import com.application.novateliveproject.repository.DownloadRepository;
import com.application.novateliveproject.repository.FrameRepository;

import java.util.ArrayList;
import java.util.List;

public class DownloadViewModel extends AndroidViewModel {
    private DownloadRepository repository;
    private LiveData<List<DownloadModel>> downloadData;


    public DownloadViewModel(@NonNull Application application) {
        super(application);
        repository = new DownloadRepository(application);
        downloadData = repository.getFrameData();
    }

    public LiveData<List<DownloadModel>> getDownLoadData() {
        return downloadData;
    }

    public void insert(List<DownloadModel> data) {
        repository.insertFrame(data);
    }

}
