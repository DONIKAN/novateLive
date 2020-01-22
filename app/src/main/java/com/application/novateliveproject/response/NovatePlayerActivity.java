package com.application.novateliveproject.response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.application.novateliveproject.NovatePlayerAdapter;
import com.application.novateliveproject.R;
import com.application.novateliveproject.model.FrameData;
import com.application.novateliveproject.model.FrameValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NovatePlayerActivity extends AppCompatActivity {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ConstraintLayout constraintLayout;
    ArrayList<ViewPager> viewPagers = new ArrayList<>();
    ArrayList<FrameResponse> frameResponses = new ArrayList<>();
    ArrayList<FrameData> frameData = new ArrayList<>();
    ArrayList<FrameValue> frameDataTo;
    FrameData frameDataModel;
    ArrayList<FrameValue> frameValue;
    FrameResponse frameResponseModel = new FrameResponse();
    FrameValue frameValueModel;
    int screenWidth, screenHeight;
    int coordX, coordY, frameHeight, frameWidth;
    float percentX, percentY, percentHeight, percentWidth;
    float currentX, currentY, currentHeight, currentWidth;
    int currentPage = 0;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novate_player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        constraintLayout = findViewById(R.id.novatePlayer);
        getCurrentScreenResulation();


        ///data to arraylist
        //add data to frame
//        frameDataModel = new FrameData();
//        frameDataModel.setCoord_x(String.valueOf(0));
//        frameDataModel.setCoord_y(String.valueOf(0));
//        frameDataModel.setHeight(String.valueOf(448));
//        frameDataModel.setWidth(String.valueOf(720));
//
//        frameValue = new ArrayList<>();
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.first);
//        frameValueModel.setDuration(200);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("video");
//        frameValueModel.setUrl(R.drawable.second);
//        frameValueModel.setDuration(200);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.third);
//        frameValueModel.setDuration(100);
//        frameValue.add(frameValueModel);
//
//        frameDataModel.setValues(frameValue);
//
//        frameData.add(frameDataModel);
//
//        frameDataModel = new FrameData();
//        frameDataModel.setCoord_x(String.valueOf(0));
//        frameDataModel.setCoord_y(String.valueOf(448));
//        frameDataModel.setHeight(String.valueOf(448));
//        frameDataModel.setWidth(String.valueOf(720));
//
//        frameValue = new ArrayList<>();
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.third);
//        frameValueModel.setDuration(300);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.first);
//        frameValueModel.setDuration(300);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.second);
//        frameValueModel.setDuration(400);
//        frameValue.add(frameValueModel);
//
//        frameDataModel.setValues(frameValue);
//
//        frameData.add(frameDataModel);
//
//        frameDataModel = new FrameData();
//        frameDataModel.setCoord_x(String.valueOf(0));
//        frameDataModel.setCoord_y(String.valueOf(896));
//        frameDataModel.setHeight(String.valueOf(448));
//        frameDataModel.setWidth(String.valueOf(720));
//
//        frameValue = new ArrayList<>();
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.second);
//        frameValueModel.setDuration(300);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("image");
//        frameValueModel.setUrl(R.drawable.third);
//        frameValue.add(frameValueModel);
//
//        frameValueModel = new FrameValue();
//        frameValueModel.setType("video");
//        frameValueModel.setUrl(R.drawable.first);
//        frameValueModel.setDuration(200);
//        frameValue.add(frameValueModel);
//
//        frameDataModel.setValues(frameValue);
//
//        frameData.add(frameDataModel);
//        //////data added to frame
//
//        frameResponseModel.setData(frameData);
//        frameResponseModel.setFrame_height("1844");
//        frameResponseModel.setFrame_width("1024");
//        frameResponses.add(frameResponseModel);

        ///data insertion complete


        for (int i = 0; i < frameResponses.get(0).getData().size(); i++) {
            getXY(i);
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(Math.round(currentWidth), Math.round(currentHeight));
            ViewPager viewPager = new ViewPager(this);
            viewPager.setLayoutParams(lparams);
            viewPager.setX(currentX);
            viewPager.setY(currentY);
//            viewPager.setBackgroundResource(R.drawable.second);
            this.constraintLayout.addView(viewPager);
            viewPagers.add(viewPager);

        }
        for (int k = 0; k < frameResponses.get(0).getData().size(); k++) {
            frameDataTo = new ArrayList<>();
            for (int t = 0; t < frameResponses.get(0).getData().get(k).getValues().size(); t++) {
                frameDataTo.add(frameResponses.get(0).getData().get(k).getValues().get(t));
            }
            NovatePlayerAdapter adapter = new NovatePlayerAdapter(this, frameDataTo);
            viewPagers.get(k).setAdapter(adapter);
//            final Handler handler = new Handler();
//            final int finalK = 0;
//            final Runnable Update = new Runnable() {
//                public void run() {
//                    if (currentPage == frameResponses.get(0).getData().size() ) {
//                        currentPage = 0;
//                    }
//                    viewPagers.get(finalK).setCurrentItem(currentPage++, true);
//                }
//            };
//
//            timer = new Timer(); // This will create a new Thread
//            timer.schedule(new TimerTask() { // task to be scheduled
//                @Override
//                public void run() {
//                    handler.post(Update);
//                }
//            }, DELAY_MS, PERIOD_MS);
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

    public void getXY(int position) {
        coordX = Integer.parseInt(frameResponses.get(0).getData().get(position).getCoord_x());
        coordY = Integer.parseInt(frameResponses.get(0).getData().get(position).getCoord_y());
        percentX = (coordX * 100) / Integer.parseInt(frameResponses.get(0).getFrame_width());
        percentY = (coordY * 100) / Integer.parseInt(frameResponses.get(0).getFrame_height());
        currentX = (percentX * screenWidth) / 100;
        currentY = (percentY * screenHeight) / 100;
        if (currentX == 0.0) {
            currentX = Integer.parseInt(frameResponses.get(0).getData().get(position).getCoord_x());
        }
        if (currentY == 0.0) {
            currentY = Integer.parseInt(frameResponses.get(0).getData().get(position).getCoord_y());
        }
        frameHeight = Integer.parseInt(frameResponses.get(0).getData().get(position).getHeight());
        frameWidth = Integer.parseInt(frameResponses.get(0).getData().get(position).getWidth());
        percentHeight = (frameHeight * 100) / Integer.parseInt(frameResponses.get(0).getFrame_height());
        percentWidth = (frameWidth * 100) / Integer.parseInt(frameResponses.get(0).getFrame_width());
        currentHeight = (percentHeight * screenHeight) / 100;
        currentWidth = (percentWidth * screenWidth) / 100;
    }
}
