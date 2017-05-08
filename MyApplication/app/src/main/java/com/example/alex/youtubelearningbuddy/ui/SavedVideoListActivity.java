package com.example.alex.youtubelearningbuddy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.adapters.VideoAdapter;
import com.example.alex.youtubelearningbuddy.model.collections.VideoCollection;
import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedVideoListActivity extends AppCompatActivity implements VideoAdapter.RecyclerViewListener{

    public static final String VIDEO_COLLECTION = "video_collection";
    @BindView(R.id.topicTextView) TextView topicTextView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.emptyTextView) TextView emptyTextView;
    private VideoCollection videoCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_video_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        videoCollection = intent.getParcelableExtra(VIDEO_COLLECTION);
        topicTextView.setText(videoCollection.getCollectionName());
        recyclerView.setAdapter(new VideoAdapter(this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (videoCollection.getVideos() != null  && videoCollection.getVideos().size() != 0) {
            ((VideoAdapter) recyclerView.getAdapter()).upload(videoCollection.getVideos());
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void lastItemIsOnTheScreen() {
        // not used here
    }

    @Override
    public void videoItemWasClicked(int position) {
        Intent intent = new Intent(this, VideoDetailsActivity.class);
        intent.putExtra(VideoDetailsActivity.SELECTED_VIDEO_ID, videoCollection.getVideos().get(position));
        startActivity(intent);
    }
}
