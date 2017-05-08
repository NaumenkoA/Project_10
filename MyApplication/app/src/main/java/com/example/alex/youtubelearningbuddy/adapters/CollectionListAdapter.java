package com.example.alex.youtubelearningbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.model.collections.CollectionList;
import com.example.alex.youtubelearningbuddy.model.collections.VideoCollection;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class CollectionListAdapter extends BaseAdapter implements Consumer <CollectionList> {

    private Context context;
    private CollectionList collection;

    public CollectionListAdapter (Context context, CollectionList list) {
        this.context = context;
        collection = list;
    }

    @Override
    public int getCount() {
        if (collection != null && collection.getCollectionList() != null) {
            return collection.getCollectionList().size();
        } else {
            return 0;
        }
       }

    @Override
    public Object getItem(int position) {
        return collection.getCollectionList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(context).inflate(R.layout.topic_list_item, null);
            holder = new ViewHolder();
            holder.listName = (TextView) convertView.findViewById(R.id.list_name);
            holder.videoCount = (TextView) convertView.findViewById(R.id.video_count);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        VideoCollection videoCollection = collection.getCollectionList().get(position);

        holder.listName.setText(videoCollection.getCollectionName());

        switch (videoCollection.getVideos().size()) {
            case 0:
                holder.videoCount.setText(R.string.no_videos);
                break;
            case 1:
                holder.videoCount.setText(R.string.one_video);
                break;
            default:
                holder.videoCount.setText(collection.getCollectionList().get(position).getVideos().size() + " videos");
        }
        return convertView;
    }


    @Override
    public void accept(@NonNull CollectionList collectionList) throws Exception {
        collection = collectionList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
         TextView listName;
         TextView videoCount;
    }
}
