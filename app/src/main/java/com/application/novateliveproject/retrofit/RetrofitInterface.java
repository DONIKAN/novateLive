package com.application.novateliveproject.retrofit;

import com.application.novateliveproject.Constants;
import com.application.novateliveproject.model.EventModel;
import com.application.novateliveproject.response.FrameResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @FormUrlEncoded
    @POST(Constants.INITIATE_METHOD)
    Call<EventModel> eventConnect(@Field("channel") String channelName,
                                   @Field("deviceName") String deviceName,
                                  @Field("deviceId") String deviceId);
    @FormUrlEncoded
    @POST(Constants.CAMPAIGN_METHOD)
    Call<FrameResponse> eventPublish(@Field("campaign_id") String campaignId);

}
