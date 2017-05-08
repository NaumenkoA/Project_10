package com.example.alex.youtubelearningbuddy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.adapters.CollectionListAdapter;
import com.example.alex.youtubelearningbuddy.helpers.VideoCollectionListLoader;
import com.example.alex.youtubelearningbuddy.model.collections.CollectionList;
import com.example.alex.youtubelearningbuddy.model.collections.VideoCollection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TopicListFragment extends Fragment {
    @BindView(R.id.topicListView) ListView topicListView;
    @BindView(R.id.fab) ImageButton fab;
    @BindView(R.id.fragmentLayout) RelativeLayout mainLayout;
    CollectionList collectionList = new CollectionList();
    CollectionListAdapter adapter;
    Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_topic_list, container, false);
    ButterKnife.bind(this, view);
    return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList <VideoCollection> savedList = VideoCollectionListLoader.getSavedCollections(getActivity());
        collectionList.setCollectionList(savedList);
        adapter = new CollectionListAdapter(getActivity(),
                collectionList);
        topicListView.setAdapter(adapter);
        disposable = collectionList.asObservable().subscribe(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.dispose();
        VideoCollectionListLoader.saveCollections(getActivity(), collectionList.getCollectionList());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter title for a new topic");
                final EditText inputEditText = new EditText(getActivity());
                inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputEditText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (inputEditText.getText().length() == 0) {
                            Toast.makeText(getActivity(), "Topic name can't be empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                       boolean added = collectionList.add(new VideoCollection(inputEditText.getText().toString()));
                        if (!added) {
                             Snackbar.make(mainLayout, "The same topic is already in the list!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

       topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoCollection collection =  collectionList.getCollectionList().get(position);
                Intent intent = new Intent(getActivity(), SavedVideoListActivity.class);
                intent.putExtra(SavedVideoListActivity.VIDEO_COLLECTION, collection);
                startActivity(intent);
                   }
        });
    }
}
