package com.example.alex.youtubelearningbuddy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.youtubelearningbuddy.R;
import com.example.alex.youtubelearningbuddy.model.comments.CommentDetails;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.VideoHolder> {

    private ArrayList<CommentDetails> commentDetails;
    private Context context;
    private CommentRecyclerViewListener listener;
    private TextView textViewNoData;

    public interface CommentRecyclerViewListener {
       void lastItemIsOnTheScreen();
        void videoItemWasClicked(int position);
    }

    public CommentAdapter(Context context, CommentRecyclerViewListener listener, TextView textView) {
        this.context = context;
        this.listener = listener;
        this.textViewNoData = textView;
    }

    @Override
    public CommentAdapter.VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.video_comment_layout, parent, false);
       return new VideoHolder(view);
       }

    @Override
    public void onBindViewHolder(CommentAdapter.VideoHolder holder, int position) {
        holder.bindComment(commentDetails.get(position));
        if ((position) == getItemCount() -1) {
            listener.lastItemIsOnTheScreen();
        }
    }

    @Override
    public int getItemCount() {
        return  (commentDetails != null && commentDetails.size() > 0) ? commentDetails.size():0;
   }

    public void upload(ArrayList<CommentDetails> data) {
        commentDetails = data;
        if (commentDetails != null && commentDetails.size() > 0) {
            textViewNoData.setVisibility(View.GONE);
        } else
                {textViewNoData.setVisibility(View.VISIBLE);
        }
        this.notifyDataSetChanged();
    }

    class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView authorTextView;
        TextView dateTextView;
        TextView commentTextView;

         VideoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            authorTextView = (TextView) itemView.findViewById(R.id.authorTextView);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.commentDateTextView);
        }

        void bindComment(CommentDetails commentDetails) {
            authorTextView.setText(commentDetails.getAuthorDisplayName());
            commentTextView.setText(commentDetails.getTextDisplay());
            dateTextView.setText(commentDetails.getFormattedPublishedAt());
           }

        @Override
        public void onClick(View view) {
            listener.videoItemWasClicked(getAdapterPosition());
        }
    }
}
