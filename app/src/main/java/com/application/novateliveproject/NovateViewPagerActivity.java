package com.application.novateliveproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.fragments.ImageFragment;
import com.application.novateliveproject.model.DownloadModel;
import com.application.novateliveproject.model.EventModel;
import com.application.novateliveproject.model.FrameData;
import com.application.novateliveproject.model.FrameValue;
import com.application.novateliveproject.response.FrameResponse;
import com.application.novateliveproject.retrofit.RetrofitInstance;
import com.application.novateliveproject.retrofit.RetrofitInterface;
import com.application.novateliveproject.view_model.FrameViewModel;
import com.pusher.client.Pusher;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovateViewPagerActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    List<DownloadModel>storagePath=new ArrayList<>();
    List<ViewPager> viewPagers = new ArrayList<>();
    List<FrameResponse> frameResponses = new ArrayList<>();
    List<FrameData> frameData = new ArrayList<>();
    List<FrameEntity> FrameDatabaseData= new ArrayList<>();
    FrameEntity frameEntity;
    List<FrameValue> frameDataTo;
    FrameData frameDataModel;
    List<FrameValue> frameValue;
    FrameResponse frameResponseModel = new FrameResponse();
    FrameValue frameValueModel;
    float screenWidth, screenHeight;
    float screenHeightRatio, screenWidthRatio;
    int currentPage;
    int currentPagePosition;
    Long viewDuration;
    private RetrofitInterface retrofitInterface;
    String campaignId;
    private boolean apiHit=false;
    private FrameViewModel frameViewModel;
    private long downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novate_view_pager);

        retrofitInterface= RetrofitInstance.getFrameData().create(RetrofitInterface.class);
        campaignId=getIntent().getStringExtra(Constants.CAMPAIGN_ID);

        Intent stickyService = new Intent(this, StickyService.class);
        startService(stickyService);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        constraintLayout = findViewById(R.id.novateViewPager);
        getCurrentScreenResulation();
        //database config
        frameViewModel = ViewModelProviders.of(this).get(FrameViewModel.class);
        frameViewModel.getFrameData().observe(this, new Observer<List<FrameEntity>>() {
            @Override
            public void onChanged(List<FrameEntity> frameEntities) {
                FrameDatabaseData.addAll(frameEntities);
                setImageToPlayer();
            }
        });
    }//end of on create

    public void setImageToPlayer(){
        float height = Float.parseFloat(FrameDatabaseData.get(0).getFrame_height());
        float width = Float.parseFloat(FrameDatabaseData.get(0).getFrame_width());
            screenHeightRatio = screenHeight / height;
            screenWidthRatio = screenWidth / width;
                for (int i = 0; i < FrameDatabaseData.get(0).getData().size(); i++) {
                    ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(Math.round(Integer.parseInt(FrameDatabaseData.get(0).getData().get(i).getWidth()) * screenWidthRatio), Math.round(Integer.parseInt(FrameDatabaseData.get(0).getData().get(i).getHeight()) * screenHeightRatio));
                    ViewPager viewPager = new ViewPager(this);
                    viewPager.setLayoutParams(lparams);
                    viewPager.setX(Integer.parseInt(FrameDatabaseData.get(0).getData().get(i).getCoord_x()) * screenWidthRatio);
                    viewPager.setY(Integer.parseInt(FrameDatabaseData.get(0).getData().get(i).getCoord_y()) * screenHeightRatio);
                    viewPager.setId(i + 1);
                    this.constraintLayout.addView(viewPager);
                    NovateFragmentPagerAdapter adapter = new NovateFragmentPagerAdapter(getSupportFragmentManager(), FrameDatabaseData.get(0).getData().get(i).getValues(), i);
                    adapter.setCurrentPosition(0);
                    viewPager.setAdapter(adapter);
                    viewPagers.add(viewPager);
                }
    }

    public void getCurrentScreenResulation() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        Log.e("Width", "" + screenWidth);
        Log.e("height", "" + screenHeight);
    }

    public void chagePage(int pos) {
        NovateFragmentPagerAdapter adapter = (NovateFragmentPagerAdapter) viewPagers.get(pos).getAdapter();
        int currentPage = adapter.getCurrentPosition();
            if (currentPage < FrameDatabaseData.get(0).getData().get(pos).getValues().size() - 1) {
                adapter.setCurrentPosition(++currentPage);
                viewPagers.get(pos).setAdapter(adapter);
            } else {
                adapter.setCurrentPosition(0);
                viewPagers.get(pos).setAdapter(adapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        confirmCloseOrderDialog();
    }

    public void confirmCloseOrderDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Confirm close App").setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                    Intent intentStartervice = new Intent(NovateViewPagerActivity.this, StickyService.class);
                    stopService(intentStartervice);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create();
            builder.show();
    }


}

