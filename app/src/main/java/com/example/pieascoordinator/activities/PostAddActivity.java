package com.example.pieascoordinator.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.GroupListAdapter;
import com.example.pieascoordinator.adapters.GroupListAdapter.TYPE;
import com.example.pieascoordinator.database.PostGroupsContract;
import com.example.pieascoordinator.database.PostLinksContract;
import com.example.pieascoordinator.database.PostsContract;
import com.example.pieascoordinator.datatypes.PostGroup;
import com.example.pieascoordinator.utilities.AppDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostAddActivity extends AppCompatActivity implements AppDialog.DialogEvents {

    private static final String TAG = "PostAddActivity";
    public static final int DIALOG_ID_CANCEL = 1;

    private EditText mPostTitle, mPostContent;
    private RecyclerView mPostGroups;
    private GroupListAdapter mPostGroupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_activity_post_add);

        mPostTitle = findViewById(R.id.editTextPostTitle);
        mPostContent = findViewById(R.id.editTextPostContent);
        mPostGroups = findViewById(R.id.recyclerViewPostGroups);

        mPostGroups.setLayoutManager(new LinearLayoutManager(this));
        mPostGroupsAdapter = new GroupListAdapter(this, MainActivity.mPostGroups, GroupListAdapter.TYPE.POST_GROUP);
        mPostGroups.setAdapter(mPostGroupsAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String postTitle = mPostTitle.getText().toString();
        String postContent = mPostContent.getText().toString();

        switch (id) {
            case R.id.action_done:
                Log.d(TAG, "onOptionsItemSelected: mPostTitle.length() -> " + mPostTitle.length());
                if (mPostTitle.length() <= 0) {
                    Toast.makeText(this, "Post Title is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                Log.d(TAG, "onOptionsItemSelected: mPostContent.length() -> " + mPostContent.length());
                if (mPostContent.length() <= 0) {
                    Toast.makeText(this, "Post Content is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                Log.d(TAG, "onOptionsItemSelected: mAdapter.getSelectedPostGroups().size() -> " + mPostGroupsAdapter.getSelectedList().size());
                if (mPostGroupsAdapter.getSelectedList().size() <= 0) {
                    Toast.makeText(this, "Select at-least one Post Group", Toast.LENGTH_SHORT).show();
                    break;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
                String datetime = simpleDateFormat.format(new Date());
                Log.d(TAG, "onOptionsItemSelected: datetime -> " + datetime);
                ContentResolver contentResolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(PostsContract.Columns.POST_TITLE, postTitle);
                contentValues.put(PostsContract.Columns.POST_DATETIME, datetime);
                contentValues.put(PostsContract.Columns.POST_CONTENT, postContent);
                Log.d(TAG, "onOptionsItemSelected: MainActivity.CURRENT_USER.getId() -> " + MainActivity.CURRENT_USER.getId());
                contentValues.put(PostsContract.Columns.POST_AUTHOR_ID, MainActivity.CURRENT_USER.getId());
                Uri newPostUri = contentResolver.insert(PostsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "onOptionsItemSelected: newPostUri -> " + newPostUri);
                long newPostId = PostsContract.getPostId(newPostUri);

                ArrayList<PostGroup> selectedPostGroups = (ArrayList<PostGroup>) mPostGroupsAdapter.getSelectedList();
                for(PostGroup postGroup : selectedPostGroups) {
                    contentValues = new ContentValues();
                    contentValues.put(PostLinksContract.Columns.POST_LINK_POST_GROUP_ID, postGroup.getId());
                    contentValues.put(PostLinksContract.Columns.POST_LINK_POST_ID, newPostId);
                    contentResolver.insert(PostLinksContract.CONTENT_URI, contentValues);
                }

                Toast.makeText(this, "New Post Created by " + MainActivity.CURRENT_USER.getUsername() + " : " + MainActivity.CURRENT_USER.getId(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_cancel:
                if (postTitle.length() > 0 || postContent.length() > 0 || mPostGroupsAdapter.getSelectedList().size() > 0) {
                    AppDialog dialog = new AppDialog();
                    Bundle args = new Bundle();
                    args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL);
                    args.putString(AppDialog.DIALOG_MESSAGE, "This post data will be lost.\nDo you really want to cancel?");
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), null);
                } else {
                    finish();
                }
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: default case");
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: starts");
        if (dialogId == DIALOG_ID_CANCEL) {
            Log.d(TAG, "onPositiveDialogResult: Cancel");
            finish();
        }
        Log.d(TAG, "onPositiveDialogResult: ends");
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: starts");
        if (dialogId == DIALOG_ID_CANCEL) {
            Log.d(TAG, "onPositiveDialogResult: Cancel");
        }
        Log.d(TAG, "onNegativeDialogResult: ends");
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: starts");
        if (dialogId == DIALOG_ID_CANCEL) {
            Log.d(TAG, "onPositiveDialogResult: Cancel");
        }
        Log.d(TAG, "onDialogCancelled: ends");
    }

}
