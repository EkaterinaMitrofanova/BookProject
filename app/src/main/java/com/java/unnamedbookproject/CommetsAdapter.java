package com.java.unnamedbookproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommetsAdapter extends RecyclerView.Adapter<CommetsAdapter.RecyclerViewHolder>{

    private List<Comment> comments;

    public CommetsAdapter(List<Comment> comments) {
        if (comments == null){
            this.comments = new ArrayList<>();
        } else {
            this.comments = comments;
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.commentTitle.setText(comments.get(position).getTitle());
        holder.commentText.setText(comments.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView commentTitle, commentText;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            commentTitle = (TextView)itemView.findViewById(R.id.commentTitle);
            commentText = (TextView)itemView.findViewById(R.id.commentText);
        }
    }
}
