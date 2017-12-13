package com.geliddroid.mysore.Apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by levin on 12-11-2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://testlink4clients.com";
    public static final String BASE_url = "http://192.168.1.20/DailyGospel/index.php/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getGospelClient() {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder().baseUrl(BASE_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit1;
    }
}
