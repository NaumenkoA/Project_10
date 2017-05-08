package com.example.alex.youtubelearningbuddy.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
     public static YouTube getYouTubeApi () {
        return new Retrofit.Builder()
                .baseUrl(YouTube.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(YouTube.class);
    }
}
