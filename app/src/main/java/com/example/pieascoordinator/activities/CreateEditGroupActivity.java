package com.example.pieascoordinator.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.GroupUsersAdapter;
import com.example.pieascoordinator.database.UserLinksContract;
import com.example.pieascoordinator.datatypes.PostGroup;
import com.example.pieascoordinator.datatypes.User;
import com.example.pieascoordinator.utilities.AppDialog;

import java.util.ArrayList;

public class CreateEditGroupActivity extends AppCompatActivity implements View.OnClickListener, AppDialog.DialogEvents {

    public enum GROUP {POST, USER};
    public enum PURPOSE {CREATE, EDIT};

    private static final String TAG = "CreateEditGroupActivity";
    public static final int DIALOG_ID_CANCEL = 1;
    public static final String GROUP_TYPE = "group_type";
    public static final String PURPOSE_TYPE = "purpose_type";

    private EditText mGroupTitle;
    private Button mAddUserGroup, mAddSingleUser;
    private RecyclerView mRecyclerView;

    private GROUP mGroupType;
    private PURPOSE mPurposeType;
    private GroupUsersAdapter mAdapter;
    private ArrayList<User> mUsersList = new ArrayList<>();
    private PostGroup postGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_group);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mPurposeType = (PURPOSE) bundle.get(PURPOSE_TYPE);
            mGroupType = (GROUP) bundle.get(GROUP_TYPE);
            String title = mPurposeType.toString() + mGroupType.toString() + " GROUP";
            setTitle(title);

            if(mGroupType == GROUP.POST && mPurposeType == PURPOSE.EDIT) {
                postGroup = (PostGroup) bundle.get(PostGroup.class.getSimpleName());
                mGroupTitle.setText(postGroup != null ? postGroup.getTitle() : "null");
            }
        } else {
            Toast.makeText(this, "Error loading bundle: Finishing Activity", Toast.LENGTH_SHORT).show();
            finish();
        }

        mGroupTitle = findViewById(R.id.editTextGroupTitle);
        mAddUserGroup = findViewById(R.id.buttonAddUserGroup);
        mAddSingleUser = findViewById(R.id.buttonAddSingleUser);

        mAddUserGroup.setOnClickListener(this);
        mAddSingleUser.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recyclerViewGroupUsers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroupUsersAdapter(this, mUsersList);
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_group, menu);
        if(mPurposeType == PURPOSE.CREATE) {
            menu.findItem(R.id.action_group_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starts");

        switch (item.getItemId()) {
            case R.id.action_group_done:
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_group_cancel:
                if(mGroupTitle.length() > 0 || mUsersList.size() > 0) {
                    AppDialog dialog = new AppDialog();
                    Bundle args = new Bundle();
                    args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL);
                    args.putString(AppDialog.DIALOG_MESSAGE, "The post text will be lost.\nDo you really want to cancel?");
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), null);
                } else {
                    finish();
                }
                break;
            case R.id.action_group_delete:
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
            default:
                Log.d(TAG, "onOptionsItemSelected: default case");
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonAddUserGroup:
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonAddSingleUser:
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {

    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {

    }

    @Override
    public void onDialogCancelled(int dialogId) {

    }

    public ArrayList<User> queryGroupUsers() {
        Log.d(TAG, "queryGroupUsers: starts");
        ArrayList<User> users = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(UserLinksContract.CONTENT_URI, null, null, null, null);

        if(cursor != null) {
            while(cursor.moveToFirst()) {
                users.add(new User(cursor));
            }
            cursor.close();
        }

        Log.d(TAG, "queryGroupUsers: returning -> " + users);
        return users;
    }

}
