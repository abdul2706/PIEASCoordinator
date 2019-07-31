package com.example.pieascoordinator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pieascoordinator.R;

import com.example.pieascoordinator.datatypes.Post;
import com.example.pieascoordinator.datatypes.User;

public class CursorPostsAdapter extends RecyclerView.Adapter<CursorPostsAdapter.PostViewHolder> {

    private static final String TAG = "CursorPostsAdapter";

    private Context mContext;
    private Cursor mCursor;
    private OnPostClickListener mListener;

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public CursorPostsAdapter(Context context, Cursor cursor, OnPostClickListener listener) {
        Log.d(TAG, "CursorPostsAdapter: starts");
        this.mContext = context;
        this.mCursor = cursor;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if (mCursor != null && mCursor.getCount() > 0) {
            Log.d(TAG, "onBindViewHolder: mCursor.count() -> " + mCursor.getCount());
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Post post = new Post(mCursor);
            User postAuthor = User.queryUser(mContext, post.getId());

            holder.postTitle.setText(post.getTitle());
            holder.postAuthor.setVisibility(View.VISIBLE);
            holder.postAuthor.setText(postAuthor.getUsername());
            holder.postDateTime.setVisibility(View.VISIBLE);
            holder.postDateTime.setText(post.getDateTime().toString());

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: starts -> holder is clicked");
                    mListener.onPostClick(post);
                }
            };

            holder.postView.setOnClickListener(buttonListener);
        } else {
            holder.postTitle.setText(mContext.getString(R.string.post_adapter_instructions));
            holder.postAuthor.setVisibility(View.GONE);
            holder.postDateTime.setVisibility(View.GONE);
            holder.postView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null || mCursor.getCount() == 0) {
            return 1;   // because we populate a single ViewHolder with instructions
        } else {
            return mCursor.getCount();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        Log.d(TAG, "swapCursor: newCursor -> " + newCursor);
        Log.d(TAG, "swapCursor: mCursor -> " + mCursor);
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, getItemCount());
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "PostViewHolder";
        View postView;
        TextView postTitle, postAuthor, postDateTime;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postView = itemView;
            this.postTitle = itemView.findViewById(R.id.pliPostTitle);
            this.postAuthor = itemView.findViewById(R.id.uliDepartment);
            this.postDateTime = itemView.findViewById(R.id.uliBatch);
        }

    }

}
