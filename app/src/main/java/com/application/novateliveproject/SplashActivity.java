package com.application.novateliveproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class SplashActivity extends AppCompatActivity {
    private String isCampaign;
    private String campaignId;
    private String isConnected;
    private String channelName;
    private String downloadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isCampaign = CommonMethods.getPrefData(Constants.CAMPAIGN_CREATED, getApplicationContext());
        campaignId = CommonMethods.getPrefData(Constants.CAMPAIGN_ID, getApplicationContext());
        isConnected = CommonMethods.getPrefData(Constants.CHANNEL_CONNECTION, getApplicationContext());
        channelName= CommonMethods.getPrefData(Constants.CHANNEL_NAME, getApplicationContext());
        downloadTask= CommonMethods.getPrefData(Constants.DOWNLOAD_PENDING, getApplicationContext());

            if (downloadTask.equals(Constants.DOWNLOAD_PENDING)){
                String campaignId = CommonMethods.getPrefData(Constants.CAMPAIGN_ID, getApplicationContext());
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                MainActivity.downLoadTask=true;
                intent.putExtra(Constants.CAMPAIGN_ID, campaignId);
                intent.putExtra(Constants.CHANNEL_NAME, channelName);
                startActivity(intent);
                finish();

            }else if (isCampaign.equals("CampaignCreated")) {
                String campaignId = CommonMethods.getPrefData(Constants.CAMPAIGN_ID, getApplicationContext());
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                MainActivity.viewPager=true;
                intent.putExtra(Constants.CAMPAIGN_ID, campaignId);
                intent.putExtra(Constants.CHANNEL_NAME, channelName);
                startActivity(intent);
                finish();

            }else if (isConnected.equals(Constants.CHANNEL_CONNECTED)){
                MainActivity.noCampaign=true;
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(Constants.CHANNEL_NAME, channelName);
                startActivity(intent);
                finish();

            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                    }
                }).start();
            }
    }
}
