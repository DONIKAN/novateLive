package com.application.novateliveproject.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.application.novateliveproject.dao.DownloadDao;
import com.application.novateliveproject.dao.FrameDao;
import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.model.DownloadModel;
import com.application.novateliveproject.room_database.FrameRoomDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DownloadRepository {
    private DownloadDao downloadDao;
    private LiveData<List<DownloadModel>> downloadData;

    public DownloadRepository(Application application) {
        FrameRoomDatabase db = FrameRoomDatabase.geDatabase(application);
        downloadDao = db.downloadDao();
        downloadData = downloadDao.downlData();
    }

    public LiveData<List<DownloadModel>> getFrameData() {
        return downloadData;
    }

    public void insertFrame(List<DownloadModel> downloadModel) {
        new DownloadRepository.insertAsyncDownload(downloadDao).execute(downloadModel);

    }

    public static class insertAsyncDownload extends AsyncTask<List<DownloadModel>, Void, Void> {

        private DownloadDao downloadDao;

        insertAsyncDownload(DownloadDao dao) {
            downloadDao = dao;
        }

        @Override
        protected Void doInBackground(final List<DownloadModel>... downloadDaos) {
            downloadDao.createFrame(downloadDaos[0]);
            return null;
        }
    }

    public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final FrameDao mDao;

        public PopulateDbAsync(FrameRoomDatabase db) {
            mDao = db.frameDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            return null;
        }
    }
}
