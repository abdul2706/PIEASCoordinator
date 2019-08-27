package com.example.pieascoordinator.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.CursorListAdapter;
import com.example.pieascoordinator.adapters.GroupListAdapter;
import com.example.pieascoordinator.adapters.UserListAdapter;
import com.example.pieascoordinator.database.PostGroupsContract;
import com.example.pieascoordinator.database.UsersContract;
import com.example.pieascoordinator.datatypes.UserGroup;
import com.example.pieascoordinator.utilities.AppDialog;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AppDialog.DialogEvents {

    private static final String TAG = "ListActivity";
    public static final int LOADER_USER_ID = 1;
    public static final int LOADER_POST_GROUP_ID = 2;
    public static final String INTENT_LIST_TYPE = "list_type";
    public static final int DIALOG_ID_CANCEL = 1;

    public enum LIST_TYPE {LIST_USER, LIST_POST_GROUP}

    private RecyclerView recyclerView;
    private UserListAdapter mUserListAdapter;
    private GroupListAdapter mGroupListAdapter;
    private LIST_TYPE mListType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mListType = (LIST_TYPE) bundle.get(INTENT_LIST_TYPE);
            if(mListType == LIST_TYPE.LIST_USER) {
                String title = "Users List";
                setTitle(title);
                mUserListAdapter = new UserListAdapter(this, null, null);
                recyclerView.setAdapter(mUserListAdapter);
            } else if (mListType == LIST_TYPE.LIST_POST_GROUP) {
                String title = "PostGroups List";
                setTitle(title);
                mGroupListAdapter = new GroupListAdapter(this, MainActivity.mUserGroups, GroupListAdapter.TYPE.USER_GROUP);
                recyclerView.setAdapter(mGroupListAdapter);
            }
        } else {
            Toast.makeText(this, "ListActivity Error: Bundle is null", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (mListType == LIST_TYPE.LIST_USER) {
            getSupportLoaderManager().initLoader(LOADER_USER_ID, null, this);
        } else if (mListType == LIST_TYPE.LIST_POST_GROUP) {
            getSupportLoaderManager().initLoader(LOADER_POST_GROUP_ID, null, this);
        }

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Log.d(TAG, "onOptionsItemSelected: DONE Clicked");
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra(CreateEditGroupActivity.INTENT_GROUP_USER_LIST, mListAdapter);
                break;
            case R.id.action_cancel:
                Log.d(TAG, "onOptionsItemSelected: CANCEL Clicked");
                if(mListAdapter.getItemCount() > 0) {
                    AppDialog dialog = new AppDialog();
                    Bundle args = new Bundle();
                    args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL);
                    args.putString(AppDialog.DIALOG_MESSAGE, "The selected list will be lost.\nAre you sure you want to cancel?");
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader() called with: id = [" + id + "]");
        switch (id) {
            case LOADER_USER_ID:
                return new CursorLoader(this, UsersContract.CONTENT_URI, null, null, null, null);
            case LOADER_POST_GROUP_ID:
                return new CursorLoader(this, PostGroupsContract.CONTENT_URI, null, null, null, null);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id = " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: starts");
        mListAdapter.swapCursor(data);
        Log.d(TAG, "onLoadFinished: count -> " + mListAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mListAdapter.swapCursor(null);
        Log.d(TAG, "onLoaderReset: ends");
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: starts");
        if (dialogId == DIALOG_ID_CANCEL) {
            finish();
        }
        Log.d(TAG, "onPositiveDialogResult: ends");
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: starts");
        Log.d(TAG, "onNegativeDialogResult: ends");
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: starts");
        Log.d(TAG, "onDialogCancelled: ends");
    }

}
