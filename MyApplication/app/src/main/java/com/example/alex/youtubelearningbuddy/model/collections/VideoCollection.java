package com.example.alex.youtubelearningbuddy.model.collections;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class VideoCollection implements Parcelable {
    private String collectionName;

    public ArrayList<VideoItem> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideoItem> videos) {
        this.videos = videos;
    }

    void addVideo(VideoItem videoItem) {
        videos.add(videoItem);
    }

    private ArrayList<VideoItem> videos;

    public VideoCollection (String name) {
        collectionName = name;
        videos = new ArrayList<>();
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.collectionName);
        dest.writeTypedList(this.videos);
    }

    protected VideoCollection(Parcel in) {
        this.collectionName = in.readString();
        this.videos = in.createTypedArrayList(VideoItem.CREATOR);
    }

    public static final Creator<VideoCollection> CREATOR = new Creator<VideoCollection>() {
        @Override
        public VideoCollection createFromParcel(Parcel source) {
            return new VideoCollection(source);
        }

        @Override
        public VideoCollection[] newArray(int size) {
            return new VideoCollection[size];
        }
    };
}
