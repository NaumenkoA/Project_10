package com.example.alex.youtubelearningbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.helpers.VideoCollectionListLoader;
import com.example.alex.youtubelearningbuddy.model.collections.CollectionList;
import com.example.alex.youtubelearningbuddy.model.videos.Snippet;
import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailsActivity extends AppCompatActivity {

    public static final String SELECTED_VIDEO_ID = "selected_video_id";
    public static final String VIDEO_ID = "video_id";
    public static final String IS_ADDING_TO_LISTS_AVAILABLE = "is_adding_to_lists_available";
    private static final int CHOOSE_LIST_REQUEST_CODE = 1;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.videoImageView)
    ImageView videoImageView;
    @BindView(R.id.showCommentsButton)
    Button showCommentsButton;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    VideoItem videoItem;
    boolean isMenuAvailable;
    CollectionList collectionList = new CollectionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        isMenuAvailable = intent.getBooleanExtra(IS_ADDING_TO_LISTS_AVAILABLE, false);
        videoItem = intent.getParcelableExtra(SELECTED_VIDEO_ID);
        Snippet snippet = videoItem.getSnippet();
        titleTextView.setText(snippet.getTitle());
        Picasso.with(this).load(snippet.getThumbnails().getHigh().getUrl()).into(videoImageView);
        descriptionTextView.setText(snippet.getDescription());
        if (isMenuAvailable) {
            getSupportActionBar().setTitle("Add this video to collection:");
            getSupportActionBar().show();
        }

        showCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoDetailsActivity.this, VideoCommentsActivtiy.class);
                intent.putExtra(VIDEO_ID, videoItem.getId().getVideoId());
                startActivity(intent);
            }
        });
    }

    public void playVideo(View view) {
        Intent playVideoIntent = new Intent(this, YouTubePlayActivity.class);
        playVideoIntent.putExtra(YouTubePlayActivity.VIDEO_ID, videoItem.getId().getVideoId());
        startActivity(playVideoIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isMenuAvailable) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return (super.onCreateOptionsMenu(menu));
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_list:
                collectionList.setCollectionList(VideoCollectionListLoader.getSavedCollections(this));
                if (collectionList.getNamesOfLists().size() == 0) {
                    Toast.makeText(this, "No one topic was created. Create some topic first", Toast.LENGTH_LONG).show();
            } else {
                    Intent intent = new Intent(this, SelectListActivity.class);
                    startActivityForResult(intent, CHOOSE_LIST_REQUEST_CODE);
                   }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_LIST_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                boolean isAdded = collectionList.addVideoToCollection(data.getStringExtra(SelectListActivity.CHOSEN_LIST_NAME),
                        videoItem);
                if (isAdded) {
                    Toast.makeText(this, "Video is successfully added to topic " + "\"" + data.getStringExtra(SelectListActivity.CHOSEN_LIST_NAME) + "\""
                            , Toast.LENGTH_SHORT).show();
                    VideoCollectionListLoader.saveCollections(this, collectionList.getCollectionList());
                } else {
                    Toast.makeText(this, "Some error has occurred:(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
