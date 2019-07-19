package com.example.pieascoordinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private static final String TAG = "CommentAdapter";

    private ArrayList<Comment> mComments;

    public CommentAdapter(ArrayList<Comment> comments) {
        this.mComments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.mCommentAuthor.setText(mComments.get(position).getAuthor());
        holder.mCommentContent.setText(mComments.get(position).getContent());
        holder.mCommentDateTime.setText(mComments.get(position).getDateTime().toString());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setComments(ArrayList<Comment> comments) {
        this.mComments = comments;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "CommentViewHolder";

        private TextView mCommentAuthor;
        private TextView mCommentContent;
        private TextView mCommentDateTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mCommentAuthor = itemView.findViewById(R.id.cliCommentAuthor);
            mCommentContent = itemView.findViewById(R.id.cliCommentContent);
            mCommentDateTime = itemView.findViewById(R.id.cliCommentDateTime);
        }
    }

}
