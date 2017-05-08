package com.example.alex.youtubelearningbuddy.api;


import com.example.alex.youtubelearningbuddy.model.comments.Comments;
import com.example.alex.youtubelearningbuddy.model.videos.Item;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTube {
    String YOUTUBE_BASE_URL ="https://www.googleapis.com";
    String API_KEY = "AIzaSyBGEJkLgWlSJvEax-_xTGIkeWPSdqntp4o";

    @GET ("youtube/v3/search?part=snippet&order=rating&type=video&key=" + API_KEY)
    Call<Item> getVideos (@Query("q") String query, @Query("pageToken") String pageToken);

    @GET ("youtube/v3/commentThreads?part=snippet&textFormat=plainText&key=" + API_KEY)
    Call<Comments> getComments (@Query("videoId") String query, @Query("pageToken") String pageToken);

}
