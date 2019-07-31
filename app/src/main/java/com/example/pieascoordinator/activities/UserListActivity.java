package com.example.pieascoordinator.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.CursorPostsAdapter;
import com.example.pieascoordinator.adapters.CursorUserAdapter;
import com.example.pieascoordinator.database.PostsContract;
import com.example.pieascoordinator.database.UsersContract;

import java.security.InvalidParameterException;

public class UserListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "UserListActivity";
    public static final int LOADER_USER_ID = 2;

    private CursorUserAdapter mPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle(R.string.title_activity_user_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostAdapter = new CursorUserAdapter(this, null);
        recyclerView.setAdapter(mPostAdapter);

        getSupportLoaderManager().initLoader(LOADER_USER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader() called with: id = [" + id + "]");

        switch (id) {
            case LOADER_USER_ID:
                return new CursorLoader(this, UsersContract.CONTENT_URI, null, null, null, null);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id = " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: starts");
        mPostAdapter.swapCursor(data);
        Log.d(TAG, "onLoadFinished: count -> " + mPostAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mPostAdapter.swapCursor(null);
    }

}
