package com.application.novateliveproject.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.application.novateliveproject.MainActivity;
import com.application.novateliveproject.NovateViewPagerActivity;
import com.application.novateliveproject.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int url;
    private int pagerPostion;
    private Long duration;
    VideoView videoView;
    private boolean _hasLoadedOnce = false; // your boolean field


    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, int param2,Long param3) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putLong(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        savedInstanceState.putBundle("",savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getInt(ARG_PARAM1);
            pagerPostion = getArguments().getInt(ARG_PARAM2);
            duration=getArguments().getLong(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoView = view.findViewById(R.id.videoView);
        loadVideo();
        return view;
    }

    private void loadVideo(){
        String video_url = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;
        Uri video = Uri.parse(video_url);
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
//                ((NovateViewPagerActivity)getActivity()).chagePage(pagerPostion);
                ((MainActivity)getActivity()).chagePage(pagerPostion);
            }
        });
    }

    private void changePage(){
        final Handler handler = new Handler();
        final int finalK = 0;
        final Runnable Update = new Runnable() {
            public void run() {
            }
        };
        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0,duration);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
    }
}
