package com.application.novateliveproject;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;

import com.application.novateliveproject.model.FrameValue;
import com.application.novateliveproject.response.FrameResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NovatePlayerAdapter extends PagerAdapter {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    int playingPosition;
    int currentPage = 0;
    boolean playing = false;
    Timer timer;
    Activity mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<FrameValue> frameResponses;
//    ImageView imageView;
//    VideoView videoView;
    int pagerPosition;
    public NovatePlayerAdapter(Activity context, ArrayList frame) {
        mContext = context;
        frameResponses = frame;
//        this. pagerPosition = pagerPosition;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return frameResponses.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.video_view_layout, container, false);
        VideoView   videoView = (VideoView) itemView.findViewById(R.id.videoViewPager);
        ImageView    imageView = (ImageView) itemView.findViewById(R.id.imageViewPager);
            if (frameResponses.get(position).getType().equals("video")) {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    Uri video = Uri.parse("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
                    videoView.setVideoURI(video);
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playingPosition = 10;
                    playing = false;
                }
            });
            container.addView(itemView);
        } else {
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageResource(frameResponses.get(position).getUrl());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(itemView);
        }


//        setData();
//        final Handler handler = new Handler();
//        final int finalK = 0;
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == frameResponses.size()) {
//                    currentPage = 0;
//                }
//                currentPage++;
//                mContext.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        notifyDataSetChanged();
//                    }
//                });
//
//            }
//        };
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 0, frameResponses.get(currentPage).getDuration());

        return itemView;
    }


//    private void setData() {
//        if (frameResponses.get(currentPage).getType().equals("video")) {
//            imageView.setVisibility(View.GONE);
//            videoView.setVisibility(View.VISIBLE);
//            Uri video = Uri.parse("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
//            videoView.setVideoURI(video);
//            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//
//                    mediaPlayer.start();
//                }
//            });
//            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    playingPosition = 10;
//                    playing = false;
//                }
//            });
//        }else {
//            videoView.setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageResource(frameResponses.get(currentPage).getUrl());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//        final Handler handler = new Handler();
//        final int finalK = 0;
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == frameResponses.size()) {
//                    currentPage = 0;
//                }
//                currentPage++;
//                mContext.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                       setData();
//                    }
//                });
//
//            }
//        };
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 0, frameResponses.get(currentPage).getDuration());
//    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
