package com.example.alex.youtubelearningbuddy.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.alex.youtubelearningbuddy.R;


public class MainActivity extends AppCompatActivity {

    private static final String SEARCH_VIDEO_FRAGMENT = "search_video_fragment";
    private static final String TOPIC_LIST_FRAGMENT = "topic_list_fragment";
    Fragment topicListFragment;
    Fragment searchVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        getSupportFragmentManager().beginTransaction().
                add(R.id.placeholder, new TopicListFragment(), TOPIC_LIST_FRAGMENT).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                    topicListFragment = getSupportFragmentManager().findFragmentByTag(TOPIC_LIST_FRAGMENT);
                        if (topicListFragment == null) {
                            topicListFragment = new TopicListFragment();
                        }
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.placeholder, topicListFragment, TOPIC_LIST_FRAGMENT)
                            .addToBackStack(null)
                            .commit();
                        break;
                    case 1:
                    searchVideoFragment = getSupportFragmentManager().findFragmentByTag(SEARCH_VIDEO_FRAGMENT);
                    if (searchVideoFragment == null) {
                        searchVideoFragment = new SearchVideoFragment();
                    }
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.placeholder, searchVideoFragment, SEARCH_VIDEO_FRAGMENT).
                                addToBackStack(null)
                                .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
