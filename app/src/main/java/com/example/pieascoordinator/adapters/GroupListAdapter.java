package com.example.pieascoordinator.adapters;

import android.content.Context;
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
import com.example.pieascoordinator.datatypes.UserGroup;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private static final String TAG = "GroupListAdapter";

    public enum TYPE {POST_GROUP, USER_GROUP}

    private Context mContext;
    private TYPE mType;
    private ArrayList<PostGroup> mPostGroupList = new ArrayList<>();
    private ArrayList<PostGroup> mPostGroupListSelected = new ArrayList<>();
    private ArrayList<UserGroup> mUserGroupList = new ArrayList<>();
    private ArrayList<UserGroup> mUserGroupListSelected = new ArrayList<>();

    public GroupListAdapter(Context context, ArrayList<?> arrayList, TYPE type) {
        this.mContext = context;
        this.mType = type;
        if (mType == TYPE.POST_GROUP) {
            mPostGroupList = (ArrayList<PostGroup>) arrayList;
        } else if (mType == TYPE.USER_GROUP) {
            mUserGroupList = (ArrayList<UserGroup>) arrayList;
        }
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
        final int index = position;
        if (mType == TYPE.POST_GROUP) {
            if (mPostGroupList.size() > 0) {
                holder.mCheckBox.setVisibility(View.VISIBLE);
                holder.mGroupTitle.setVisibility(View.VISIBLE);
                holder.mGroupTitle.setText(mPostGroupList.get(position).getTitle());
                holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.d(TAG, "onCheckedChanged: adding postGroup -> " + mPostGroupList.get(index));
                            mPostGroupListSelected.add(mPostGroupList.get(index));
                        } else {
                            Log.d(TAG, "onCheckedChanged: removing postGroup -> " + mPostGroupList.get(index));
                            mPostGroupListSelected.remove(mPostGroupList.get(index));
                        }
                    }
                });
            } else {
                holder.mGroupTitle.setText(mContext.getString(R.string.groups_adapter_instructions));
                holder.mCheckBox.setVisibility(View.GONE);
                holder.mGroupTitle.setVisibility(View.GONE);
            }
        } else if (mType == TYPE.USER_GROUP) {
            if (mUserGroupList.size() > 0) {
                holder.mCheckBox.setVisibility(View.VISIBLE);
                holder.mGroupTitle.setVisibility(View.VISIBLE);
                holder.mGroupTitle.setText(mUserGroupList.get(position).getTitle());
                holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.d(TAG, "onCheckedChanged: adding userGroup -> " + mUserGroupList.get(index));
                            mUserGroupListSelected.add(mUserGroupList.get(index));
                        } else {
                            Log.d(TAG, "onCheckedChanged: removing userGroup -> " + mUserGroupList.get(index));
                            mUserGroupListSelected.remove(mUserGroupList.get(index));
                        }
                    }
                });
            } else {
                holder.mGroupTitle.setText(mContext.getString(R.string.groups_adapter_instructions));
                holder.mCheckBox.setVisibility(View.GONE);
                holder.mGroupTitle.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mType == TYPE.POST_GROUP) {
            return mPostGroupList.size();
        } else if (mType == TYPE.USER_GROUP) {
            return mUserGroupList.size();
        }
        return 0;
    }

    public ArrayList<?> getSelectedList() {
        if (mType == TYPE.POST_GROUP) {
            return mPostGroupListSelected;
        } else if (mType == TYPE.USER_GROUP) {
            return mUserGroupListSelected;
        }
        return null;
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
