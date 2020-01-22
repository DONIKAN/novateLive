package com.application.novateliveproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.application.novateliveproject.fragments.ImageFragment;
import com.application.novateliveproject.fragments.VideoFragment;
import com.application.novateliveproject.model.FrameValue;

import java.util.ArrayList;

public class NovateFragmentPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<FrameValue> frameResponses;
    int pagerPosition,duration;
    int currentPosition = 0;
    public NovateFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList frame, int pagerPosition) {
        super(fragmentManager);
        frameResponses = frame;
        this. pagerPosition = pagerPosition;
    }


    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition(){
        return  this.currentPosition;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int position) {
            if(frameResponses.get(currentPosition).getType().equals("video")){
                return VideoFragment.newInstance(frameResponses.get(currentPosition).getUrl(),pagerPosition,frameResponses.get(currentPosition).getDuration()) ;
            }else {
                return   ImageFragment.newInstance(frameResponses.get(currentPosition).getUrl(),pagerPosition,frameResponses.get(currentPosition).getDuration()) ;
            }
        }

}

