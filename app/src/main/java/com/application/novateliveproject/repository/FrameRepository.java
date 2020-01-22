package com.application.novateliveproject.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.application.novateliveproject.dao.FrameDao;
import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.room_database.FrameRoomDatabase;

import org.w3c.dom.Entity;

import java.util.List;

public class FrameRepository {
    private FrameDao frameDao;
    private LiveData<List<FrameEntity>> frameData;

    public FrameRepository(Application application) {
        FrameRoomDatabase db = FrameRoomDatabase.geDatabase(application);
        frameDao = db.frameDao();
        frameData = frameDao.getFrames();
    }

    public LiveData<List<FrameEntity>> getFrameData() {
        return frameData;
    }

    public void insertFrame(FrameEntity entity) {
        new insertAsyncTask(frameDao).execute(entity);

    }

    private static class insertAsyncTask extends AsyncTask<FrameEntity, Void, Void> {

        private FrameDao frameDao;

        insertAsyncTask(FrameDao dao) {
            frameDao = dao;
        }

        @Override
        protected Void doInBackground(final FrameEntity... frameEntities) {
            frameDao.createFrame(frameEntities[0]);
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
