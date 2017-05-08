package com.example.alex.youtubelearningbuddy.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.FrameLayout;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.api.YouTube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
public static final String VIDEO_ID = "video_id";
    private String videoId;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_play);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        videoId = getIntent().getStringExtra(VIDEO_ID);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(YouTube.API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(videoId);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Snackbar.make(frameLayout, "Sorry. Some error has occured.", Snackbar.LENGTH_LONG).show();
    }
}
