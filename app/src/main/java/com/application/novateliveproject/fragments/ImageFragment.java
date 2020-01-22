package com.application.novateliveproject.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.novateliveproject.Constants;
import com.application.novateliveproject.MainActivity;
import com.application.novateliveproject.NovateViewPagerActivity;
import com.application.novateliveproject.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement th
 * to handle interaction events.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String url;
    private int pagerPosition;
    private Long duration;

    MainActivity activity;


    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String param1, int param2,Long param3) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putLong(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
            pagerPosition = getArguments().getInt(ARG_PARAM2);
            duration = getArguments().getLong(ARG_PARAM3);
            activity = (MainActivity) getActivity();
            changePage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
//        Picasso.get().load(Constants.IMAGE_DOWNLOAD_URL+url).into(imageView);
        File imageFile = new File(url);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
//        imageView.setImageResource(url);

        return  view;
    }

    private void changePage(){

        new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                activity.chagePage(pagerPosition);
            }
        }.start();
    }



}
