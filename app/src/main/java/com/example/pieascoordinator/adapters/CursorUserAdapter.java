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
import com.example.pieascoordinator.datatypes.User;

public class CursorUserAdapter  extends RecyclerView.Adapter<CursorUserAdapter.UserViewHolder> {

    private static final String TAG = "CursorPostsAdapter";
    private Context mContext;
    private Cursor mCursor;

    public CursorUserAdapter(Context context, Cursor cursor) {
        Log.d(TAG, "CursorPostsAdapter: starts");
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

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

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "PostViewHolder";
        private View mViewHolder;
        private TextView mUserUsername, mUserDepartment, mUserBatch;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mViewHolder = itemView;
            this.mUserUsername = itemView.findViewById(R.id.uliUsername);
            this.mUserDepartment = itemView.findViewById(R.id.uliDepartment);
            this.mUserBatch = itemView.findViewById(R.id.uliBatch);
            itemView.findViewById(R.id.uliCheckBox).setVisibility(View.GONE);
            itemView.findViewById(R.id.uliUnselect).setVisibility(View.GONE);
        }

    }

}
