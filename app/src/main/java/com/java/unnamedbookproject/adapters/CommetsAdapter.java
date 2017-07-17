package com.java.unnamedbookproject.adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.model.Comment;

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
        Comment comment = comments.get(position);
        String title = comment.getTitle();
        String rank = comment.getRating();
        if (title != null && !title.equals("")) {
            holder.commentTitle.setVisibility(View.VISIBLE);
            holder.commentTitle.setText(title);
        } else {
            holder.commentTitle.setVisibility(View.GONE);
        }
        if (rank != null ) {
            holder.commentRating.setVisibility(View.VISIBLE);
            holder.commentRating.setText(rank);
        } else {
            holder.commentRating.setVisibility(View.GONE);
        }
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= 24) {
            spanned = Html.fromHtml(comments.get(position).getText(), Html.FROM_HTML_MODE_COMPACT);// for 24 api and more
        } else {
            spanned = Html.fromHtml(comments.get(position).getText()); // or for older api
        }
        holder.commentText.setText(spanned);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView commentTitle, commentText, commentRating;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            commentTitle = (TextView)itemView.findViewById(R.id.commentTitle);
            commentText = (TextView)itemView.findViewById(R.id.commentText);
            commentRating = (TextView)itemView.findViewById(R.id.rank);
        }
    }
}
