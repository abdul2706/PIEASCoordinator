package com.example.pieascoordinator.adapters;

import android.content.ContentResolver;
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
import com.example.pieascoordinator.activities.CreateEditGroupActivity;
import com.example.pieascoordinator.database.UPGLinksContract;
import com.example.pieascoordinator.datatypes.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.GroupViewHolder> {

    private static final String TAG = "UserListAdapter";

    private Context mContext;
    private ButtonClicked mButtonClicked;
    private Cursor mCursor;
    private ArrayList<User> mUsersList;

    public interface ButtonClicked {
        public void onClickUnselect(int index);
    }

    public UserListAdapter(Context context, ArrayList<User> users, ButtonClicked buttonClicked) {
        this.mContext = context;
        this.mUsersList = users;
        this.mButtonClicked = buttonClicked;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        final int index = position;

        if (mUsersList != null) {
            holder.mUserUsername.setText(mUsersList.get(position).getUsername());
            holder.mUserDepartment.setText(mUsersList.get(position).getDepartment());
            holder.mUserDepartment.setVisibility(View.VISIBLE);
            holder.mUserBatch.setText(mUsersList.get(position).getBatch());
            holder.mUserBatch.setVisibility(View.VISIBLE);
            holder.mButtonUnselect.setVisibility(View.VISIBLE);
            holder.mButtonUnselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonClicked.onClickUnselect(index);
//                    Log.d(TAG, "onClick: starts");
//                    ContentResolver resolver = mContext.getContentResolver();
//                    String where = UPGLinksContract.Columns.UPG_LINK_USER_ID + " = ? AND " + UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID + " = ?";
//                    String[] selectionArgs = {String.valueOf(mUsersList.get(index).getId()), String.valueOf(mActivity.getGroupId())};
//                    resolver.delete(UPGLinksContract.CONTENT_URI, where, selectionArgs);
//                    mUsersList.remove(index);
//                    notifyDataSetChanged();
//                    Log.d(TAG, "onClick: ends");
                }
            });
        } else {
            holder.mUserUsername.setText(mContext.getString(R.string.user_adapter_instructions));
            holder.mUserDepartment.setVisibility(View.GONE);
            holder.mUserBatch.setVisibility(View.GONE);
            holder.mButtonUnselect.setVisibility(View.GONE);
        }

        Log.d(TAG, "onBindViewHolder: ends");
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public void updateUsersList(ArrayList<User> users) {
        this.mUsersList = users;
        notifyDataSetChanged();
    }

    public ArrayList<User> getUsersList() {
        return mUsersList;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PostViewHolder";

        private View mViewHolder;
        private TextView mUserUsername, mUserDepartment, mUserBatch;
        private ImageButton mButtonUnselect;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mViewHolder = itemView;
            this.mUserUsername = itemView.findViewById(R.id.uliUsername);
            this.mUserDepartment = itemView.findViewById(R.id.uliDepartment);
            this.mUserBatch = itemView.findViewById(R.id.uliBatch);
            this.mButtonUnselect = itemView.findViewById(R.id.uliUnselect);
            itemView.findViewById(R.id.uliCheckBox).setVisibility(View.GONE);
            itemView.findViewById(R.id.uliEdit).setVisibility(View.GONE);
            itemView.findViewById(R.id.uliRemove).setVisibility(View.GONE);
        }
    }

}
