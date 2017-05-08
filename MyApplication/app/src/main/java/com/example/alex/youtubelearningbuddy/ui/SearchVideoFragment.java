package com.example.alex.youtubelearningbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.adapters.VideoAdapter;
import com.example.alex.youtubelearningbuddy.api.Service;
import com.example.alex.youtubelearningbuddy.model.videos.Item;
import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchVideoFragment extends Fragment implements VideoAdapter.RecyclerViewListener {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private ArrayList<VideoItem> videoArrayList = new ArrayList<>();
    private String userRequest;
    private Item item;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_video_fragment, container, false);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.searchProgressBar);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        && v.getText().length() != 0
                        && !v.getText().toString().equals(userRequest)) {
                    hideSoftKeyBoard();
                    showVideosByUserRequest();
                    return true;
                }
                hideSoftKeyBoard();
                return false;
            }
        });
        recyclerView.setAdapter(new VideoAdapter(getActivity(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((VideoAdapter) recyclerView.getAdapter()).upload(videoArrayList);
    }

    private void showVideosByUserRequest() {
        showLoading(true);
        videoArrayList = new ArrayList<>();
        userRequest= searchEditText.getText().toString();
        Service.getYouTubeApi().getVideos(userRequest, "")
                .enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        onSuccessResponse(response);
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        onFailureResponse();
                    }
                });
    }

    public void onFailureResponse() {
        showLoading(false);
        Toast.makeText(getActivity(), "Some error occurred. Please try again:(", Toast.LENGTH_SHORT).show();
    }

    private void onSuccessResponse(Response<Item> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            item = response.body();
            List<VideoItem> itemList = item.getItems();
                for (VideoItem item: itemList) {
                videoArrayList.add(item);
            }
           ((VideoAdapter) recyclerView.getAdapter()).upload(videoArrayList);
            showLoading(false);
           } else {
           onFailureResponse();
        }
    }

    @Override
    public void lastItemIsOnTheScreen() {
        if (item != null && item.getNextPageToken() != null && !item.getNextPageToken().equals("")) {
            Service.getYouTubeApi().getVideos(userRequest, item.getNextPageToken())
                    .enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            onSuccessResponse(response);
                        }

                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {
                            onFailureResponse();
                        }
                    });
        }
    }

    @Override
    public void videoItemWasClicked(int position) {
        Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
        intent.putExtra(VideoDetailsActivity.SELECTED_VIDEO_ID, videoArrayList.get(position));
        intent.putExtra(VideoDetailsActivity.IS_ADDING_TO_LISTS_AVAILABLE, true);
        startActivity(intent);
            }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
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
