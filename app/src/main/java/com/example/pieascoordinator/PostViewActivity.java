package com.example.pieascoordinator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostViewActivity extends AppCompatActivity {

    private static final String TAG = "PostViewActivity";

    private ScrollView mScrollView;
    private TextView mPostTitle, mPostContent, mPostAuthor, mPostDateTime;
    private RecyclerView mRecyclerView;
    private CommentAdapter mCommentAdapter;
    private ArrayList<Comment> mComments;
    private Post mSelectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mSelectedPost = (Post) bundle.get(Post.class.getSimpleName());
        } else {
            Toast.makeText(this, "Error Showing Post", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: Error Showing Post");
            finish();
        }

        mScrollView = findViewById(R.id.scrollView);
        mPostTitle = findViewById(R.id.textViewPostTitle);
        mPostContent = findViewById(R.id.textViewPostContent);
        mPostAuthor = findViewById(R.id.textViewPostAuthor);
        mPostDateTime = findViewById(R.id.textViewPostDateTime);

        mComments = Data.generateComments();
        Log.d(TAG, "onCreate: mComments -> " + mComments);
        mComments = filterByPost(mComments, mSelectedPost);
        Log.d(TAG, "onCreate: mComments -> " + mComments);

        mRecyclerView = findViewById(R.id.recyclerViewComments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter(mComments);
        mRecyclerView.setAdapter(mCommentAdapter);

        mPostTitle.setText(mSelectedPost.getTitle());
        mPostContent.setText(mSelectedPost.getContent());
        mPostAuthor.setText(mSelectedPost.getAuthor());
        mPostDateTime.setText(mSelectedPost.getDateTime().toString());

        mScrollView.smoothScrollTo(0, 0);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Comment> filterByPost(ArrayList<Comment> comments, Post post) {
        Log.d(TAG, "filterByPost: starts");
        ArrayList<Comment> filteredComments = new ArrayList<>();
        for(Comment comment : comments) {
            if(post.getId() == comment.getPostId()) {
                filteredComments.add(comment);
            }
        }
        Log.d(TAG, "filterByPost: filteredComments -> " + filteredComments);
        Log.d(TAG, "filterByPost: ends");
        return filteredComments;
    }

}
