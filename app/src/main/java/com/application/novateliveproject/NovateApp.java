package com.application.novateliveproject;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class NovateApp extends Application implements Application.ActivityLifecycleCallbacks {
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(NovateApp.this);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
//        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
//            Toast.makeText(activity, "App in Foreground", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
//        isActivityChangingConfigurations = activity.isChangingConfigurations();
//        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
//            Toast.makeText(activity, "App in Background", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//        isActivityChangingConfigurations = activity.isChangingConfigurations();
//        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
//            Toast.makeText(activity, "App in Background", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
    }

}

