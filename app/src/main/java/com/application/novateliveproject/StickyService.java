package com.application.novateliveproject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StickyService extends Service {
    private SharedPreferences mPrefs;
    private Handler handler=new Handler();
    private boolean onTaskRemoved=false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        onTaskRemoved=true;
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onTrimMemory(int level) {
            if (level==TRIM_MEMORY_UI_HIDDEN) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onTaskRemoved){
                            onTaskRemoved=false;
                        }else {
                            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }, 3000);
            }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
