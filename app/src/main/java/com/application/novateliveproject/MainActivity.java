package com.application.novateliveproject;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.application.novateliveproject.entity.FrameEntity;
import com.application.novateliveproject.model.DownloadModel;
import com.application.novateliveproject.model.EventModel;
import com.application.novateliveproject.response.FrameResponse;
import com.application.novateliveproject.retrofit.RetrofitInstance;
import com.application.novateliveproject.retrofit.RetrofitInterface;
import com.application.novateliveproject.room_database.FrameRoomDatabase;
import com.application.novateliveproject.view_model.DownloadViewModel;
import com.application.novateliveproject.view_model.FrameViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    final Handler handler2 = new Handler();
    final Handler handler3 = new Handler();
    final int UPDATE_PROGRESS = 1020;
    int THREAD_DELAY= 0;
    int pStatus = 0;
    RetrofitInterface retrofitInterface;
    private String deviceName;
    private String androidId;
    private String campaignId = "";
    private String downloadUrl = "", downloadFileName = "";
    private ImageView optionMenu_iv, refresh_iv;
    private TextView random_txt, completeText;
    private ConstraintLayout noCampaignLayout;
    private LinearLayout firstRandomLayout, secoDownloadLayout, thirDownloadCompleted;
    private Handler handler = new Handler();
    private String channel = "";
    private String event = "checkPlayer";
    public static boolean noCampaign=false;
    public static boolean viewPager=false;
    FrameEntity frameEntity;
    private FrameViewModel frameViewModel;
    private DownloadViewModel downloadViewModel;
    List<DownloadModel>storagePath=new ArrayList<>();
    List<DownloadModel>downloadedData=new ArrayList<>();
    List<Long>downloadId=new ArrayList<>();
    private long downloadID;
    private RelativeLayout mainLayout,mainLayoutTwo;
    int downloadedBytes;
    int columId;
    boolean downloading = true;
    float screenWidth, screenHeight;
    float screenHeightRatio, screenWidthRatio;
    List<FrameEntity> FrameDatabaseData= new ArrayList<>();
    List<ViewPager> viewPagers = new ArrayList<>();
    public static boolean downLoadTask=false;
    private long percent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout=findViewById(R.id.mainlayout);
        mainLayoutTwo=findViewById(R.id.otherView);
        //To connect with pusher and get the data
        deviceName = android.os.Build.MANUFACTURER + " " + Build.MODEL;
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        retrofitInterface = RetrofitInstance.getFrameData().create(RetrofitInterface.class);
        frameViewModel = ViewModelProviders.of(this).get(FrameViewModel.class);
        downloadViewModel=ViewModelProviders.of(this).get(DownloadViewModel.class);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getCurrentScreenResulation();

        Intent stickyService = new Intent(this, StickyService.class);
        startService(stickyService);

        initObjects();
        getSaltString();



        if (noCampaign){
            channel=getIntent().getStringExtra(Constants.CHANNEL_NAME);
            noCampaignTwo();
        }
        if (viewPager){
            campaignId=getIntent().getStringExtra(Constants.CAMPAIGN_ID);
            channel=getIntent().getStringExtra(Constants.CHANNEL_NAME);
            dataFromDataBase();
            THREAD_DELAY=0;
        }if (downLoadTask){
            campaignId=getIntent().getStringExtra(Constants.CAMPAIGN_ID);
            channel=getIntent().getStringExtra(Constants.CHANNEL_NAME);
            downloadedData();
        }

        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("a3ea89652c7f1f1216d2", options);
        final Channel channelName = pusher.subscribe(channel);
            channelName.bind(event, new SubscriptionEventListener() {
                @Override
                public void onEvent(PusherEvent event) {
                    try {
                        String string = event.getData().toString();
                        JSONObject jsonObject = new JSONObject(string);
                        String apiData = jsonObject.getString("api");
                            if (apiData.equals("initiatePlayer")) {
                                CommonMethods.setPrefData(Constants.CHANNEL_NAME,channel,getApplicationContext());
                                CommonMethods.setPrefData(Constants.CHANNEL_CONNECTION,Constants.CHANNEL_CONNECTED,getApplicationContext());
                                noCampaignTwo();
                                noCampaignApi();
                            }
                            if (apiData.equals("getCampaignData")) {
                                campaignId = jsonObject.getString("campaign_id");
//                                firstTask();
                                publishCampaignApi();
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
        pusher.connect();

        onClickListeners();


        //database config
        frameViewModel = ViewModelProviders.of(this).get(FrameViewModel.class);
       
//        firstTask();
    }

    private void initObjects() {
        optionMenu_iv = findViewById(R.id.optionMenu_iv);
        random_txt = findViewById(R.id.random_txt);
        completeText = findViewById(R.id.txt_completeView);
        refresh_iv = findViewById(R.id.refresh_iv);

        firstRandomLayout = findViewById(R.id.firstRandomLayout);
        secoDownloadLayout = findViewById(R.id.seconDownloadLayout);
        thirDownloadCompleted = findViewById(R.id.thirDownloadCompleted);
        noCampaignLayout = findViewById(R.id.noCampaignLayout);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            findViewById(R.id.mainlayout).setAnimation(animation);

    }

    private void onClickListeners() {
        refresh_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSaltString();
            }
        });

        optionMenu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, optionMenu_iv);
                getMenuInflater().inflate(R.menu.toolbar_menu, popupMenu.getMenu());
                popupMenu.show();


            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void thirdTask() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            completeText.setText(pStatus + "% Completed");
                            if (pStatus == 100) {
                                handler.postAtTime(new Runnable() {
                                    @Override
                                    public void run() {
                                        firstRandomLayout.setVisibility(View.GONE);
                                        secoDownloadLayout.setVisibility(View.GONE);
                                        thirDownloadCompleted.setVisibility(View.VISIBLE);
                                        hideView(thirDownloadCompleted, R.anim.slide_down);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(2000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
//                                                startActivity(new Intent(MainActivity.this, ViewPagerActiviy.class));
//                                                startActivity(new Intent(MainActivity.this, NovateViewPagerActivity.class));
                                                Intent intent = new Intent(MainActivity.this, NovateViewPagerActivity.class);
                                                intent.putExtra(Constants.CAMPAIGN_ID, campaignId);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).start();
                                    }
                                }, 2000);
                            }

                        }

                    });


                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(100); //thread will take approx 3 seconds to finish,change its value according to your needs
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }



    private void hideView(final View view, int id) {
        Animation animation = AnimationUtils.loadAnimation(this, id);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(R.id.top).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.top).setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(animation);
    }
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>No Campaign Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) {// length of the random string.

//            if (salt.length() == 3) {
//
//                salt.append("");
//            } else {

            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            Log.d("Index", "getSaltString: " + SALTCHARS.charAt(index));
            salt.append(SALTCHARS.charAt(index));
//            }
        }
        String saltStr = salt.toString();
        random_txt.setText(saltStr);
        channel = random_txt.getText().toString().trim();
        return saltStr;
    }

    private void noCampaignTwo() {

        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                firstRandomLayout.setVisibility(View.GONE);
                secoDownloadLayout.setVisibility(View.GONE);
                thirDownloadCompleted.setVisibility(View.GONE);
                findViewById(R.id.top).setVisibility(View.GONE);
                noCampaignLayout.setVisibility(View.VISIBLE);
            }
        }, 0);
    }

    private void noCampaignApi() {
        Call<EventModel> getSection = retrofitInterface.eventConnect(channel, deviceName, androidId);
        getSection.enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                if (response.isSuccessful()) {
//                    sectionNames.clear();
//                    dialog.dismiss();
                    if (response.body().isStatus()) {
                    } else {
                        Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<No Campaign Method Ends<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Methods Download Camapaign>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    private void firstTask() {
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                firstRandomLayout.setVisibility(View.GONE);
                secoDownloadLayout.setVisibility(View.VISIBLE);
                thirDownloadCompleted.setVisibility(View.GONE);
                noCampaignLayout.setVisibility(View.GONE);
//                thirdTask();
            }
        }, 0);
    }

    private void publishCampaignApi() {
        Call<FrameResponse> getSection = retrofitInterface.eventPublish(campaignId);
        getSection.enqueue(new Callback<FrameResponse>() {
            @Override
            public void onResponse(Call<FrameResponse> call, Response<FrameResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                            frameEntity=new FrameEntity();
                            frameEntity.setData(response.body().getData());
                            frameEntity.setFrame_height("1344");
                            frameEntity.setFrame_width("720");
                            CommonMethods.setPrefData(Constants.CAMPAIGN_ID,campaignId,getApplicationContext());
                            for (int i=0;i<frameEntity.getData().size();i++) {
                                for (int j = 0; j < frameEntity.getData().get(i).getValues().size(); j++) {
                                   if (downLoadTask){
                                       if (frameEntity.getData().get(i).getValues().get(j).getSize()< downloadedData.get(j).getDownloaded()){
                                           String imgUrl = Constants.IMAGE_DOWNLOAD_URL + frameEntity.getData().get(i).getValues().get(j).getUrl();
                                           String fileName = frameEntity.getData().get(i).getValues().get(j).getUrl().substring(frameEntity.getData().get(i).getValues().get(j).getUrl().lastIndexOf('/') + 1);
                                           beginDownload("Novate", imgUrl, fileName, i, j);
                                       }else {
                                           storagePath.add(downloadedData.get(j));
                                       }
                                   }else {
                                       String imgUrl = Constants.IMAGE_DOWNLOAD_URL + frameEntity.getData().get(i).getValues().get(j).getUrl();
                                       String fileName = frameEntity.getData().get(i).getValues().get(j).getUrl().substring(frameEntity.getData().get(i).getValues().get(j).getUrl().lastIndexOf('/') + 1);
                                       beginDownload("Novate", imgUrl, fileName, i, j);
                                   }
                                }
                            }

                    } else {
                        Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FrameResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void beginDownload(String folderName, String urlPath, String fileTitle,int framePosition,int valuePosition) {
        final DownloadModel model=new DownloadModel();
        File file = new File(getApplicationContext().getExternalFilesDir(null) + "/" + folderName + "/", fileTitle);
        model.setImagePath(file+"");
        model.setframePosition(framePosition);
        model.setValuePosition(valuePosition);
        Log.e("ImagePath",file+"");
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        DownloadManager.Request request = null;// Set if download is allowed on roaming network
        request = new DownloadManager.Request(Uri.parse(urlPath))
                .setTitle(fileTitle)// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                // Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setMimeType("*/*")
                .setAllowedOverRoaming(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request.setRequiresCharging(false);
        }

        final DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        if (file.exists()){
            file.delete();
        }
        model.setDownloadId(downloadID);
        storagePath.add(model);
        downloadId.add(downloadID);
        firstTask();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==UPDATE_PROGRESS){
                    downloadedBytes=0;
                    for (int i=0;i<storagePath.size();i++){
                        downloadedBytes+=storagePath.get(i).getDownloaded();
                    }
                     percent=(int) ((downloadedBytes * 100l) / 8342305);
                    completeText.setText(percent+" % "+"Complete");
                }
                super.handleMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
//                    q.setFilterById(downloadID);
                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    columId =cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                    for (int i=0;i<storagePath.size();i++){
                        if (columId==storagePath.get(i).getDownloadId()){
                            storagePath.get(i).setDownloaded(bytes_downloaded);
                        }
                    }
                    //Post message to UI Thread
                    Message msg = handler.obtainMessage();
                    msg.what = UPDATE_PROGRESS;
                    //msg.obj = statusMessage(cursor);
                    msg.arg1 = bytes_downloaded;
                    msg.arg2 = bytes_total;
                    handler.sendMessage(msg);
                    cursor.close();
                }
            }
        }).start();

    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                downloading=false;
                Snackbar.make(mainLayout,"Downloading Completed",Snackbar.LENGTH_SHORT).show();
                if (storagePath.size()>0){
                    for (int i=0;i<storagePath.size();i++){
                        frameEntity.getData().get(storagePath.get(i).framePosition).getValues().get(storagePath.get(i).getValuePosition()).setUrl(storagePath.get(i).imagePath);
                    }
                    if (frameEntity.getData().size()>0) {
                        frameViewModel.insert(frameEntity);
                        THREAD_DELAY=2000;
                        CommonMethods.setPrefData(Constants.DOWNLOAD_PENDING,"Not Any Pending Download",getApplicationContext());
                        CommonMethods.setPrefData(Constants.CAMPAIGN_CREATED,"CampaignCreated",getApplicationContext());
                        downLoadTask=false;
                        dataFromDataBase();
                    }else {
                        Snackbar.make(mainLayout,"Unable To Start Player",Snackbar.LENGTH_SHORT).show();
                    }
                }
                Log.e("DownloadId",downloadID+"");
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Download Campaign Ends<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>View Pager Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public void dataFromDataBase(){
        new Handler().postDelayed(new Runnable() {
            public void run() {

                frameViewModel.getFrameData().observe(MainActivity.this, new Observer<List<FrameEntity>>() {
                    @Override
                    public void onChanged(List<FrameEntity> frameEntities) {
                        FrameDatabaseData.addAll(frameEntities);
                        if (FrameDatabaseData.get(0).getData().size()>0){
                            mainLayoutTwo.setVisibility(View.GONE);
                            setImageToPlayer();
                        }
                    }
                });
            }
        }, THREAD_DELAY);   //1 seconds
    }

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
            this.mainLayout.addView(viewPager);
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
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Ends View Pager Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Confirm close App").setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
                Intent intentStartervice = new Intent(MainActivity.this, StickyService.class);
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
    public void downloadedData(){
        downloadViewModel.getDownLoadData().observe(MainActivity.this, new Observer<List<DownloadModel>>() {
            @Override
            public void onChanged(List<DownloadModel> downloadModels) {
                downloadedData.addAll(downloadModels);
                if (downloadedData.size()>0){
                    firstTask();
                    publishCampaignApi();
                }

            }

        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(onDownloadComplete);
        if (percent >0 && percent<100){
            downloadViewModel.insert(storagePath);
            CommonMethods.setPrefData(Constants.DOWNLOAD_PENDING,Constants.DOWNLOAD_PENDING,getApplicationContext());

        }else {
            CommonMethods.setPrefData(Constants.DOWNLOAD_PENDING,"Not Any Pending Download",getApplicationContext());
        }
        super.onDestroy();
    }
}
