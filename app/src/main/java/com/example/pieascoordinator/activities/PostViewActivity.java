package com.example.pieascoordinator.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.pieascoordinator.adapters.CursorCommentsAdapter;
import com.example.pieascoordinator.R;
import com.example.pieascoordinator.database.CommentsContract;
import com.example.pieascoordinator.datatypes.Post;
import com.example.pieascoordinator.datatypes.User;
import com.example.pieascoordinator.utilities.AppDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class PostViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AppDialog.DialogEvents, CursorCommentsAdapter.ButtonClickHandler {

    private static final String TAG = "PostViewActivity";
    public static final int LOADER_COMMENT_ID = 1;
    public static final int DIALOG_ID_DELETE = 2;

    private ScrollView mScrollView;
    private TextView mPostTitle, mPostContent, mPostAuthor, mPostDateTime;
    private RecyclerView mRecyclerView;
    private CursorCommentsAdapter mCommentAdapter;
    private Post mSelectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSelectedPost = (Post) bundle.get(Post.class.getSimpleName());
            setTitle("Post#: " + mSelectedPost.getId());
        } else {
            Toast.makeText(this, "Error Showing Post, Try Again", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: Error Showing Post");
            finish();
        }

        FloatingActionButton fab = findViewById(R.id.fabAddComment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        mScrollView = findViewById(R.id.scrollView);
        mPostTitle = findViewById(R.id.textViewPostTitle);
        mPostContent = findViewById(R.id.textViewPostContent);
        mPostAuthor = findViewById(R.id.textViewPostAuthor);
        mPostDateTime = findViewById(R.id.textViewPostDateTime);

        mRecyclerView = findViewById(R.id.recyclerViewComments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CursorCommentsAdapter(this, null, this);
        mRecyclerView.setAdapter(mCommentAdapter);

        mPostTitle.setText(mSelectedPost.getTitle());
        mPostContent.setText(mSelectedPost.getContent());
        mPostAuthor.setText(User.queryUser(this, mSelectedPost.getId()).getUsername());
        mPostDateTime.setText(mSelectedPost.getDateTime().toString());

        mScrollView.smoothScrollTo(0, 0);
        getSupportLoaderManager().initLoader(LOADER_COMMENT_ID, null, this);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starts");
        switch (item.getItemId()) {
            case R.id.action_post_edit:
                Log.d(TAG, "onOptionsItemSelected: Edit Clicked");
                Intent intent = new Intent(PostViewActivity.this, PostAddActivity.class);
                intent.putExtra(Post.class.getSimpleName(), mSelectedPost);
                startActivity(intent);
                break;
            case R.id.action_post_delete:
                Log.d(TAG, "onOptionsItemSelected: Delete Clicked");
                AppDialog dialog = new AppDialog();
                Bundle args = new Bundle();
                args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_DELETE);
                args.putString(AppDialog.DIALOG_MESSAGE, "Do you really want to delete this post?\nThis cannot be undone.");
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), null);
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: default case");
                return super.onOptionsItemSelected(item);
        }

        Log.d(TAG, "onOptionsItemSelected: ends");
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader() called with: id = [" + id + "]");
        String sortOrder = CommentsContract.Columns.COMMENT_DATETIME;

        switch (id) {
            case LOADER_COMMENT_ID:
                return new CursorLoader(this, CommentsContract.CONTENT_URI, null, null, null, sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id = " + id);

        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: starts");
        mCommentAdapter.swapCursor(data);
        Log.d(TAG, "onLoadFinished: count -> " + mCommentAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mCommentAdapter.swapCursor(null);
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: starts");
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                break;
            default:
                Log.d(TAG, "onPositiveDialogResult: default");
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

    @Override
    public void onEditClicked() {
        Log.d(TAG, "onEditClicked: starts");
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onEditClicked: ends");
    }

    @Override
    public void onDeleteClicked() {
        Log.d(TAG, "onDeleteClicked: starts");
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDeleteClicked: ends");
    }
}
