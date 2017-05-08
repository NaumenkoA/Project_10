package com.example.alex.youtubelearningbuddy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.model.videos.Snippet;
import com.example.alex.youtubelearningbuddy.model.videos.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter <VideoAdapter.VideoHolder> {

    private ArrayList<VideoItem> videoItems;
    private Context context;
    private RecyclerViewListener listener;

    public interface RecyclerViewListener {
       void lastItemIsOnTheScreen();
        void videoItemWasClicked(int position);
    }

    public VideoAdapter (Context context, RecyclerViewListener listener) {
        this.context = context;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.video_card_layout, parent, false);
       return new VideoHolder(view);
       }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoHolder holder, int position) {
        holder.bindVideo(videoItems.get(position).getSnippet());
        if ((position) == getItemCount() -1) {
            listener.lastItemIsOnTheScreen();
        }
    }

    @Override
    public int getItemCount() {
        return (videoItems != null) ? videoItems.size():0;
    }

    public void upload(ArrayList<VideoItem> data) {
        videoItems = data;
        this.notifyDataSetChanged();
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleView;
        ImageView videoImageView;

        VideoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleView = (TextView) itemView.findViewById(R.id.titleTextView);
            videoImageView = (ImageView) itemView.findViewById(R.id.videoImageView);
        }

        void bindVideo(Snippet snippet) {
            titleView.setText(snippet.getTitle());
            Picasso.with(context).load(snippet.getThumbnails().getHigh().getUrl()).into(videoImageView);
           }

        @Override
        public void onClick(View view) {
            listener.videoItemWasClicked(getAdapterPosition());
        }
    }
}
