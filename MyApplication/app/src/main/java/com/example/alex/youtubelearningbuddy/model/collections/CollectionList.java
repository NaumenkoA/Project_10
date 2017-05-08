package com.example.alex.youtubelearningbuddy.model.collections;

import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

public class CollectionList {

   private ReplaySubject <CollectionList> notifier = ReplaySubject.create();

   public ArrayList<VideoCollection> getCollectionList() {
        return collectionList;
    }

    public CollectionList () {
        collectionList = new ArrayList<>();
    }
    public Observable<CollectionList> asObservable () {
        return notifier;
    }

    public void setCollectionList(ArrayList<VideoCollection> collectionList) {
        this.collectionList = collectionList;
        notifier.onNext(this);
    }

    private ArrayList <VideoCollection> collectionList;

    public boolean add (VideoCollection collection) {
        if (!hasSameVideoCollection(collection)) {
            collectionList.add(collection);
            notifier.onNext(this);
            return true;
        }
        return false;
    }

    private boolean hasSameVideoCollection(VideoCollection collection) {
        if (collectionList == null || collectionList.size()==0) return false;
        for (VideoCollection videoCollection:collectionList) {
            if ((collection.getCollectionName()).equals(videoCollection.getCollectionName())) return true;
        }
        return false;
    }

    public ArrayList<String> getNamesOfLists() {
        ArrayList <String> list = new ArrayList<>();
        for (VideoCollection collection: collectionList) {
            list.add (collection.getCollectionName());
        }
        return list;
    }

    public boolean addVideoToCollection(String collectionName, VideoItem videoItem) {
        int count = 0;
        for (VideoCollection collection: collectionList) {
            if (collection.getCollectionName().equals(collectionName)) {
                collection.addVideo(videoItem);
                collectionList.remove(count);
                collectionList.add (count, collection);
                return true;
            }
            count++;
        }
        return false;
    }
}
