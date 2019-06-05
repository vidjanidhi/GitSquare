package com.example.gitsqaure.API;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String ROOTURL = "https://api.github.com/repos/";



    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(36000, TimeUnit.SECONDS)
                    .connectTimeout(36000, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.HOURS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOTURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitInterface getAPIService() {

        return getClient().create(RetrofitInterface.class);

    }




}
