package com.application.novateliveproject.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final String BASE_URL = "http://3.219.184.206/novateweb/public/api/";
    public static Retrofit retrofit = null;

    public static Retrofit getFrameData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
