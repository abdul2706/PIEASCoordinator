package com.example.pieascoordinator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private static final String TAG = "PostAdapter";

    private Context context;
    private ArrayList<Post> mPosts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.mPosts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final int index = position;

        holder.mPostTitle.setText(mPosts.get(position).getTitle());
        holder.mPostAuthor.setText(mPosts.get(position).getAuthor());
        holder.mPostDateTime.setText(mPosts.get(position).getDateTime().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: index -> " + index);
                Intent intent = new Intent(context, PostViewActivity.class);
                intent.putExtra(Post.class.getSimpleName(), mPosts.get(index));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void setPosts(ArrayList<Post> posts) {
        this.mPosts = posts;
        this.notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PostViewHolder";

        private TextView mPostTitle;
        private TextView mPostAuthor;
        private TextView mPostDateTime;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mPostTitle = itemView.findViewById(R.id.pliPostTitle);
            mPostAuthor = itemView.findViewById(R.id.textViewPostAuthor);
            mPostDateTime = itemView.findViewById(R.id.textViewPostDateTime);
        }
    }

}
