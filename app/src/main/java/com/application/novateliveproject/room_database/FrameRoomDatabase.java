package com.application.novateliveproject.room_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.application.novateliveproject.dao.DownloadDao;
import com.application.novateliveproject.dao.FrameDao;
import com.application.novateliveproject.entity.DataConverter;
import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.entity.VlaueConverter;
import com.application.novateliveproject.model.DownloadModel;
import com.application.novateliveproject.repository.FrameRepository;

@Database(entities = {FrameEntity.class, DownloadModel.class}, version = 1)
@TypeConverters({DataConverter.class, VlaueConverter.class})
public abstract class FrameRoomDatabase extends RoomDatabase {
    private static volatile FrameRoomDatabase INSTANCE;
//    private static RoomDatabase.Callback sRoomDatabaseCallback =
//            new RoomDatabase.Callback() {
//
//                @Override
//                public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                    super.onOpen(db);
//                    new FrameRepository.PopulateDbAsync(INSTANCE).execute();
//                }
//            };

    public static FrameRoomDatabase geDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FrameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FrameRoomDatabase.class, "frame_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FrameDao frameDao();
    public abstract DownloadDao downloadDao();


}
