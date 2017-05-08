package com.example.alex.youtubelearningbuddy.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alex.youtubelearningbuddy.model.collections.CollectionList;
import com.example.alex.youtubelearningbuddy.model.collections.VideoCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.subjects.ReplaySubject;

public class VideoCollectionListLoader {

    private static final String PREFERENCE_KEY = "com.example.alex.youtubelearningbuddy.PREFERENCE_KEY";
    private static final String COLLECTION_LIST_KEY = "collection_list_shared_preferences_key";
    private static Type listType = new TypeToken<ArrayList<VideoCollection>>(){}.getType();

    public static ArrayList<VideoCollection> getSavedCollections (Context context) {
       SharedPreferences preferences = context.getSharedPreferences(
             PREFERENCE_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(COLLECTION_LIST_KEY, "");
        if (gson.fromJson(json, listType) != null) {
            return gson.fromJson(json, listType);
        } else {
            return new ArrayList<>();
        }
    }

    public static void saveCollections (Context context, ArrayList <VideoCollection> collectionList) {
        if (collectionList != null && collectionList.size() > 0) {
            SharedPreferences preferences = context.getSharedPreferences(
                    PREFERENCE_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(collectionList, listType);
            prefsEditor.putString(COLLECTION_LIST_KEY, json);
            prefsEditor.apply();
        }
    }
}
