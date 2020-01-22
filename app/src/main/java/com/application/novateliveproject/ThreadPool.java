package com.application.novateliveproject;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPool implements Runnable {
    ConcurrentLinkedQueue<AsyncTask> tasks = new ConcurrentLinkedQueue<AsyncTask>();
    Activity activity;
    boolean stop = false;

    public ThreadPool(Activity activity) {
        this.activity = activity;
    }

    public void stop() {
        stop = true;
    }

    public void execute(AsyncTask task) {
        tasks.add(task);
    }

    @Override
    public void run() {
        while (!stop) {
            if (tasks.size() != 0) {

                final AsyncTask task = tasks.remove();
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        task.execute();
                    }
                });

            }
        }
    }
}
