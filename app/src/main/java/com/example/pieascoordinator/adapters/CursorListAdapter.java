package com.example.pieascoordinator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.activities.ListActivity.LIST_TYPE;
import com.example.pieascoordinator.datatypes.PostGroup;
import com.example.pieascoordinator.datatypes.User;

import java.util.ArrayList;

public class CursorListAdapter extends RecyclerView.Adapter<CursorListAdapter.ListViewHolder> {

    private static final String TAG = "CursorPostsAdapter";

    private LIST_TYPE mListType;
    private Context mContext;
    private Cursor mCursor;
    private ArrayList<User> mSelectedUsers;

    public CursorListAdapter(Context context, Cursor cursor, LIST_TYPE listType) {
        Log.d(TAG, "CursorPostsAdapter: starts");
        this.mContext = context;
        this.mCursor = cursor;
        this.mListType = listType;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = new View(mContext);
        if(mListType == LIST_TYPE.LIST_USER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        } else if(mListType == LIST_TYPE.LIST_POST_GROUP) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);
        }
        return new ListViewHolder(view, mListType);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if(mListType == LIST_TYPE.LIST_USER) {
            if (mCursor != null && mCursor.getCount() > 0) {
                Log.d(TAG, "onBindViewHolder: mCursor.count() -> " + mCursor.getCount());
                if (!mCursor.moveToPosition(position)) {
                    throw new IllegalStateException("Couldn't move cursor to position " + position);
                }
                User user = new User(mCursor);
                holder.mUserUsername.setText(user.getUsername());
                holder.mUserDepartment.setVisibility(View.VISIBLE);
                holder.mUserDepartment.setText(user.getDepartment());
                holder.mUserBatch.setVisibility(View.VISIBLE);
                holder.mUserBatch.setText(user.getBatch());
            } else {
                holder.mUserUsername.setText(mContext.getString(R.string.user_adapter_instructions));
                holder.mUserDepartment.setVisibility(View.GONE);
                holder.mUserBatch.setVisibility(View.GONE);
            }
        } else if (mListType == LIST_TYPE.LIST_POST_GROUP) {
            if (mCursor != null && mCursor.getCount() > 0) {
                Log.d(TAG, "onBindViewHolder: mCursor.count() -> " + mCursor.getCount());
                if (!mCursor.moveToPosition(position)) {
                    throw new IllegalStateException("Couldn't move cursor to position " + position);
                }
                PostGroup postGroup = new PostGroup(mCursor);
                holder.mPostGroupTitle.setText(postGroup.getTitle());
                holder.mPostGroupCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
            } else {
                holder.mUserUsername.setText(mContext.getString(R.string.user_adapter_instructions));
                holder.mUserDepartment.setVisibility(View.GONE);
                holder.mUserBatch.setVisibility(View.GONE);
            }
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

    static class ListViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "PostViewHolder";

        private View mViewHolder;
        private TextView mUserUsername, mUserDepartment, mUserBatch;
        private CheckBox mUserCheckBox;

        private TextView mPostGroupTitle;
        private CheckBox mPostGroupCheckBox;

        public ListViewHolder(@NonNull View itemView, LIST_TYPE list_type) {
            super(itemView);
            this.mViewHolder = itemView;
            if(list_type == LIST_TYPE.LIST_USER) {
                this.mUserUsername = itemView.findViewById(R.id.uliUsername);
                this.mUserDepartment = itemView.findViewById(R.id.uliDepartment);
                this.mUserBatch = itemView.findViewById(R.id.uliBatch);
                this.mUserCheckBox = itemView.findViewById(R.id.uliCheckBox);
                itemView.findViewById(R.id.uliEdit).setVisibility(View.GONE);
                itemView.findViewById(R.id.uliRemove).setVisibility(View.GONE);
                itemView.findViewById(R.id.uliUnselect).setVisibility(View.GONE);
            } else if(list_type == LIST_TYPE.LIST_POST_GROUP) {
                this.mPostGroupTitle = itemView.findViewById(R.id.gliGroupTitle);
                this.mPostGroupCheckBox = itemView.findViewById(R.id.gliCheckBox);
            }
        }

    }

}
