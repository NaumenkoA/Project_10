package com.example.alex.youtubelearningbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.adapters.CommentAdapter;
import com.example.alex.youtubelearningbuddy.api.Service;
import com.example.alex.youtubelearningbuddy.model.comments.CommentDetails;
import com.example.alex.youtubelearningbuddy.model.comments.CommentItem;
import com.example.alex.youtubelearningbuddy.model.comments.Comments;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoCommentsActivtiy extends AppCompatActivity implements CommentAdapter.CommentRecyclerViewListener {

    private Comments comments;
    private ArrayList<CommentDetails> commentDetails;
    private String videoId;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_comments_activtiy);
        recyclerView = (RecyclerView) findViewById(R.id.commentsRecyclerView);
        TextView emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        progressBar = (ProgressBar) findViewById(R.id.commentsProgressBar);
        Intent intent = getIntent();
        videoId = intent.getStringExtra(VideoDetailsActivity.VIDEO_ID);
        recyclerView.setAdapter(new CommentAdapter(this, this, emptyTextView));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showVideoComments();
    }

    private void showVideoComments() {
        showLoading(true);
        commentDetails = new ArrayList<>();
        Service.getYouTubeApi().getComments(videoId, "")
                .enqueue(new Callback<Comments>() {
                    @Override
                    public void onResponse(Call<Comments> call, Response<Comments> response) {
                        onSuccessResponse(response);
                    }
                    @Override
                    public void onFailure(Call<Comments> call, Throwable t) {
                        onFailureResponse();
                    }
                });
    }
    private void onSuccessResponse(Response<Comments> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            comments = response.body();
            for (CommentItem commentItem : comments.getItems()) {
                commentDetails.add(commentItem.getSnippet().getTopLevelComment().getCommentDetails());
            }
            ((CommentAdapter) recyclerView.getAdapter()).upload(commentDetails);
            showLoading(false);
        } else {
            onFailureResponse();
        }
    }

    public void onFailureResponse() {
        showLoading(false);
        Toast.makeText(this, "Some error occurred. Please try again:(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lastItemIsOnTheScreen() {
        if (comments != null && comments.getNextPageToken() != null && !comments.getNextPageToken().equals("")) {
            Service.getYouTubeApi().getComments(videoId, comments.getNextPageToken())
                    .enqueue(new Callback<Comments>() {
                        @Override
                        public void onResponse(Call<Comments> call, Response<Comments> response) {
                            onSuccessResponse(response);
                        }

                        @Override
                        public void onFailure(Call<Comments> call, Throwable t) {
                            onFailureResponse();
                        }
                    });
        }
    }

    @Override
    public void videoItemWasClicked(int position) {
        //not used here
    }

    private void showLoading (boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }
}
