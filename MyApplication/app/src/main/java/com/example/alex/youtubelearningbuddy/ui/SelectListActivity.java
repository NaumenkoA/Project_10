package com.example.alex.youtubelearningbuddy.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.adapters.CollectionListAdapter;
import com.example.alex.youtubelearningbuddy.helpers.VideoCollectionListLoader;
import com.example.alex.youtubelearningbuddy.model.collections.CollectionList;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Function;

public class SelectListActivity extends AppCompatActivity {

    public static final String CHOSEN_LIST_NAME = "chosen_list_name";
    @BindView(R.id.topicListView) ListView listView;
    @BindView(R.id.submitButton) Button submitButton;
    CollectionListAdapter adapter;
    ArrayList<String> topicList;
    CollectionList collectionList = new CollectionList();
    int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);
        ButterKnife.bind(this);
        collectionList.setCollectionList(VideoCollectionListLoader.getSavedCollections(this));
        adapter = new CollectionListAdapter(this, collectionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (submitButton.getVisibility() == View.INVISIBLE){
                    submitButton.setVisibility(View.VISIBLE);
                }
                listView.getChildAt(selectedItem).setBackgroundColor(Color.parseColor("#ea9898"));
                selectedItem = position;
                view.setBackgroundColor(Color.parseColor("#b1b1b6"));            }

        });
    }

    @OnClick(R.id.submitButton)
    public void submit(View view) {
        Intent result = new Intent();
        result.putExtra(CHOSEN_LIST_NAME, collectionList.getCollectionList().get(selectedItem).getCollectionName());
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
