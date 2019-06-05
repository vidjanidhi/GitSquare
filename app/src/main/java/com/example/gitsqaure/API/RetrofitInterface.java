package com.example.gitsqaure.API;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("square/retrofit/contributors")
    Call<ResponseBody>getData();



}
