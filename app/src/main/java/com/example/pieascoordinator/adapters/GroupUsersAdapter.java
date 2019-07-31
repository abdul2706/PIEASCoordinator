package com.example.pieascoordinator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.activities.CreateEditGroupActivity.PURPOSE;
import com.example.pieascoordinator.datatypes.Post;
import com.example.pieascoordinator.datatypes.User;

import java.util.ArrayList;

public class GroupUsersAdapter extends RecyclerView.Adapter<GroupUsersAdapter.GroupViewHolder> {

    private static final String TAG = "GroupUsersAdapter";

    private Context mContext;
//    private Cursor mCursor;
    private ArrayList<User> mUsersList;
//    private PURPOSE mPurpose;

    interface ButtonClicked {
        public void onClickUnselect();
    }

    public GroupUsersAdapter(Context context, ArrayList<User> users) {
        this.mContext = context;
        this.mUsersList = users;
//        this.mPurpose = purpose;
    }

//    public GroupUsersAdapter(Context context, Cursor cursor, PURPOSE purpose) {
//        this.mContext = context;
//        this.mCursor = cursor;
//        this.mPurpose = purpose;
//    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final int index = position;

//        if (mPurpose == PURPOSE.EDIT) {
//            if (mCursor != null && mCursor.getCount() > 0) {
//                Log.d(TAG, "onBindViewHolder: mCursor.count() -> " + mCursor.getCount());
//                if (!mCursor.moveToPosition(position)) {
//                    throw new IllegalStateException("Couldn't move cursor to position " + position);
//                }
//
//                final User user = new User(mCursor);
//
//                holder.mUserUsername.setText(user.getUsername());
//                holder.mUserDepartment.setText(user.getDepartment());
//                holder.mUserDepartment.setVisibility(View.VISIBLE);
//                holder.mUserBatch.setText(user.getBatch());
//                holder.mUserBatch.setVisibility(View.VISIBLE);
//                holder.mButtonUnselect.setVisibility(View.VISIBLE);
//                holder.mButtonUnselect.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "Unselect Clicked", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } else {
//                holder.mUserUsername.setText(mContext.getString(R.string.user_adapter_instructions));
//                holder.mUserDepartment.setVisibility(View.GONE);
//                holder.mUserBatch.setVisibility(View.GONE);
//                holder.mButtonUnselect.setVisibility(View.GONE);
//            }
//        } else if (mPurpose == PURPOSE.CREATE) {
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
                        Toast.makeText(mContext, "Unselect Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.mUserUsername.setText(mContext.getString(R.string.user_adapter_instructions));
                holder.mUserDepartment.setVisibility(View.GONE);
                holder.mUserBatch.setVisibility(View.GONE);
                holder.mButtonUnselect.setVisibility(View.GONE);
            }
//        }

    }

    @Override
    public int getItemCount() {
//        if (mPurpose == PURPOSE.EDIT) {
//            if (mCursor == null || mCursor.getCount() == 0) {
//                return 1;   // because we populate a single ViewHolder with instructions
//            } else {
//                return mCursor.getCount();
//            }
//        } else if (mPurpose == PURPOSE.CREATE) {
            return mUsersList.size();
//        }
//        return 0;
    }

//    public Cursor swapCursor(Cursor newCursor) {
//        Log.d(TAG, "swapCursor: newCursor -> " + newCursor);
//        Log.d(TAG, "swapCursor: mCursor -> " + mCursor);
//        if (newCursor == mCursor) {
//            return null;
//        }
//
//        final Cursor oldCursor = mCursor;
//        mCursor = newCursor;
//        if (newCursor != null) {
//            // notify the observers about the new cursor
//            notifyDataSetChanged();
//        } else {
//            // notify the observers about the lack of a data set
//            notifyItemRangeRemoved(0, getItemCount());
//            notifyDataSetChanged();
//        }
//        return oldCursor;
//    }

    public void updateUsersList(ArrayList<User> users) {
        this.mUsersList = users;
        notifyDataSetChanged();
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
