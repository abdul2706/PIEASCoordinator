package com.example.pieascoordinator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.database.CommentsContract;
import com.example.pieascoordinator.database.PostsContract;

import com.example.pieascoordinator.datatypes.Comment;
import com.example.pieascoordinator.datatypes.Post;
import com.example.pieascoordinator.datatypes.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CursorCommentsAdapter extends RecyclerView.Adapter<CursorCommentsAdapter.CommentViewHolder> {

    private static final String TAG = "CursorPostsAdapter";
    private Context mContext;
    private Cursor mCursor;
    private ButtonClickHandler mButtonClickHandler;

    public interface ButtonClickHandler {
        public void onEditClicked();
        public void onDeleteClicked();
    }

    public CursorCommentsAdapter(Context context, Cursor cursor, ButtonClickHandler buttonClickHandler) {
        Log.d(TAG, "CursorPostsAdapter: starts");
        this.mContext = context;
        this.mCursor = cursor;
        this.mButtonClickHandler = buttonClickHandler;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if (mCursor != null && mCursor.getCount() > 0) {
            holder.mCommentContent.setVisibility(View.VISIBLE);
            holder.mCommentDateTime.setVisibility(View.VISIBLE);
            holder.mButtonEdit.setVisibility(View.VISIBLE);
            holder.mButtonDelete.setVisibility(View.VISIBLE);

            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Comment comment = new Comment(mCursor);
            User commentAuthor = User.queryUser(mContext, comment.getId());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
            holder.mCommentAuthor.setText(commentAuthor.getUsername());
            holder.mCommentContent.setText(comment.getContent());
            try {
                holder.mCommentDateTime.setText(simpleDateFormat.format(comment.getDateTime()));
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: e -> " + e.getMessage(), e);
            }

            holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonClickHandler.onEditClicked();
                }
            });
            holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonClickHandler.onDeleteClicked();
                }
            });
        } else {
            holder.mCommentAuthor.setText(R.string.comment_adapter_instructions);
            holder.mCommentContent.setVisibility(View.GONE);
            holder.mCommentDateTime.setVisibility(View.GONE);
            holder.mButtonEdit.setVisibility(View.GONE);
            holder.mButtonDelete.setVisibility(View.GONE);
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
        }
        return oldCursor;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "CommentViewHolder";

        private View mViewHolder;
        private TextView mCommentAuthor, mCommentContent, mCommentDateTime;
        private ImageButton mButtonEdit, mButtonDelete;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mCommentAuthor = itemView.findViewById(R.id.cliCommentAuthor);
            mCommentContent = itemView.findViewById(R.id.cliCommentContent);
            mCommentDateTime = itemView.findViewById(R.id.cliCommentDateTime);
            mButtonEdit = itemView.findViewById(R.id.cliEdit);
            mButtonDelete = itemView.findViewById(R.id.cliDelete);
        }

    }

}
