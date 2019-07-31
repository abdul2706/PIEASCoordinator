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
import com.example.pieascoordinator.datatypes.PostGroup;

import java.util.ArrayList;

public class CursorGroupsAdapter extends RecyclerView.Adapter<CursorGroupsAdapter.GroupViewHolder> {

    private static final String TAG = "CursorGroupsAdapter";

    private Context mContext;
    private Cursor mCursor;
    private ArrayList<PostGroup> mSelectedPostGroups = new ArrayList<>();

    public CursorGroupsAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if (mCursor != null && mCursor.getCount() > 0) {
            Log.d(TAG, "onBindViewHolder: mCursor.count() -> " + mCursor.getCount());

            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final PostGroup postGroup = new PostGroup(mCursor);

            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mGroupTitle.setVisibility(View.VISIBLE);
            holder.mGroupTitle.setText(postGroup.getTitle());
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        Log.d(TAG, "onCheckedChanged: adding postGroup -> " + postGroup);
                        mSelectedPostGroups.add(postGroup);
                    } else {
                        Log.d(TAG, "onCheckedChanged: removing postGroup -> " + postGroup);
                        mSelectedPostGroups.remove(postGroup);
                    }
                }
            });
        } else {
            holder.mGroupTitle.setText(mContext.getString(R.string.groups_adapter_instructions));
            holder.mCheckBox.setVisibility(View.GONE);
            holder.mGroupTitle.setVisibility(View.GONE);
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

    public ArrayList<PostGroup> getSelectedPostGroups() {
        return mSelectedPostGroups;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GroupViewHolder";
        private View mViewHolder;
        private TextView mGroupTitle;
        private CheckBox mCheckBox;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mViewHolder = itemView;
            this.mGroupTitle = itemView.findViewById(R.id.gliGroupTitle);
            this.mCheckBox = itemView.findViewById(R.id.gliCheckBox);
        }
    }

}
