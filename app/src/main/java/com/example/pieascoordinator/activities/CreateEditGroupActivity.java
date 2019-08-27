package com.example.pieascoordinator.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.UserListAdapter;
import com.example.pieascoordinator.database.PostGroupsContract;
import com.example.pieascoordinator.database.UPGLinksContract;
import com.example.pieascoordinator.database.UserGroupsContract;
import com.example.pieascoordinator.database.UserLinksContract;
import com.example.pieascoordinator.datatypes.PostGroup;
import com.example.pieascoordinator.datatypes.User;
import com.example.pieascoordinator.datatypes.UserGroup;
import com.example.pieascoordinator.utilities.AppDialog;

import java.util.ArrayList;

public class CreateEditGroupActivity extends AppCompatActivity implements View.OnClickListener, AppDialog.DialogEvents, UserListAdapter.ButtonClicked {

    private static final String TAG = "CreateEditGroupActivity";
    public static final int DIALOG_ID_CANCEL = 1;
    public static final String GROUP_TYPE = "group_type";
    public static final String PURPOSE_TYPE = "purpose_type";
    public static final int REQUEST_CODE_USERS_LIST = 1;
    public static final int REQUEST_CODE_USER_GROUPS_LIST = 2;
    public static final String INTENT_GROUP_USER_LIST = "group_user_list";

    public enum GROUP {POST, USER}
    public enum PURPOSE {CREATE, EDIT}

    private EditText mGroupTitle;
    private Button mAddUserGroup, mAddSingleUser;
    private RecyclerView mRecyclerView;

    private GROUP mGroupType;
    private PURPOSE mPurposeType;
    private UserListAdapter mAdapter;
    private ArrayList<User> mUsersList = new ArrayList<>();
    private PostGroup mPostGroup = new PostGroup(-1, "none");
    private UserGroup mUserGroup = new UserGroup(-1, "none");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_group);

        mGroupTitle = findViewById(R.id.editTextGroupTitle);
        mAddUserGroup = findViewById(R.id.buttonAddUserGroup);
        mAddSingleUser = findViewById(R.id.buttonAddSingleUser);
        mAddUserGroup.setOnClickListener(this);
        mAddSingleUser.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPurposeType = (PURPOSE) bundle.get(PURPOSE_TYPE);
            mGroupType = (GROUP) bundle.get(GROUP_TYPE);
            String title = mPurposeType.toString() + mGroupType.toString() + " GROUP";
            setTitle(title);

            Log.d(TAG, "onCreate: mGroupType -> " + mGroupType);
            Log.d(TAG, "onCreate: mPurposeType -> " + mPurposeType);

            if (mGroupType == GROUP.POST && mPurposeType == PURPOSE.EDIT) {
                mPostGroup = (PostGroup) bundle.get(PostGroup.class.getSimpleName());
                Log.d(TAG, "onCreate: mPostGroup -> " + mPostGroup);
                mGroupTitle.setText(mPostGroup.getTitle());
                mUsersList = queryGroupUsers(UPGLinksContract.CONTENT_URI);
            }

            if (mGroupType == GROUP.USER && mPurposeType == PURPOSE.EDIT) {
                mUserGroup = (UserGroup) bundle.get(UserGroup.class.getSimpleName());
                Log.d(TAG, "onCreate: mUserGroup -> " + mUserGroup);
                mGroupTitle.setText(mUserGroup != null ? mUserGroup.getTitle() : "null");
                mUsersList = queryGroupUsers(UserLinksContract.CONTENT_URI);
            }
        } else {
            Toast.makeText(this, "Error loading bundle: Finishing Activity", Toast.LENGTH_SHORT).show();
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerViewGroupUsers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UserListAdapter(this, mUsersList, this);
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_group, menu);
        if (mPurposeType == PURPOSE.CREATE) {
            menu.findItem(R.id.action_group_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starts");

        ContentResolver resolver = getContentResolver();
        ContentValues contentValues;
        Cursor cursor;

        switch (item.getItemId()) {
            case R.id.action_done:
                if (mPurposeType == PURPOSE.CREATE) {
                    Log.d(TAG, "onPositiveDialogResult: CREATE");
                    if (mGroupType == GROUP.POST) {
                        Log.d(TAG, "onPositiveDialogResult: POST starts");
                        // make sure that new PostGroup Title is not empty
                        String newGroupTitle = mGroupTitle.getText().toString();
                        mUsersList = mAdapter.getUsersList();
                        if (newGroupTitle.length() <= 0) {
                            Toast.makeText(this, "Post Group Title can't be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        // make sure that users list is not empty
                        if (mUsersList.size() <= 0) {
                            Toast.makeText(this, "Post Group Students List can't be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        // check that new PostGroupTitle is not already in the PostGroups table
                        String selection1 = PostGroupsContract.Columns.POST_GROUP_TITLE + " = ?";
                        String[] selectionArgs1 = {newGroupTitle};
                        cursor = resolver.query(PostGroupsContract.CONTENT_URI, null, selection1, selectionArgs1, null);
                        if (cursor != null && cursor.getCount() > 0) {
                            Toast.makeText(this, "This Post Group Title is already present. Kindly enter new one", Toast.LENGTH_SHORT).show();
                            cursor.close();
                            break;
                        }

                        // add new PostGroup in PostGroups Table
                        long id = mPostGroup.getId();
                        contentValues = new ContentValues();
                        contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, newGroupTitle);
                        resolver.insert(PostGroupsContract.CONTENT_URI, contentValues);

                        // iterate through all users added in this group and link them with mPostGroup
                        for (User user : mUsersList) {
                            contentValues = new ContentValues();
                            contentValues.put(UPGLinksContract.Columns.UPG_LINK_USER_ID, user.getId());
                            contentValues.put(UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID, mPostGroup.getId());
                            resolver.insert(UPGLinksContract.CONTENT_URI, contentValues);
                        }
                        Log.d(TAG, "onPositiveDialogResult: POST ends");
                    } else if (mGroupType == GROUP.USER) {
                        Log.d(TAG, "onPositiveDialogResult: USER");
                    }
                } else if (mPurposeType == PURPOSE.EDIT) {
                    Log.d(TAG, "onPositiveDialogResult: EDIT starts");
                    if (mGroupType == GROUP.POST) {
                        Log.d(TAG, "onPositiveDialogResult: POST starts");
                        // Make sure that new PostGroup Title is not empty
                        String newGroupTitle = mGroupTitle.getText().toString();
                        if (newGroupTitle.length() <= 0) {
                            Toast.makeText(this, "Post Title can't be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        // make sure that users list is not empty
                        if (mUsersList.size() <= 0) {
                            Toast.makeText(this, "Post Group Students List can't be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        // Before updating PostGroupTitle make sure that new PostTitle does not match with the old PostGroupTitle
                        if (!mPostGroup.getTitle().equals(newGroupTitle)) {
                            long id = mPostGroup.getId();
                            contentValues = new ContentValues();
                            contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, newGroupTitle);
                            resolver.update(PostGroupsContract.buildPostGroupUri(id), contentValues, null, null);
                        }

                        // Iterate through all users added in this group and link them with mPostGroup if they are not linked already
                        mUsersList = mAdapter.getUsersList();
                        for (User user : mUsersList) {
                            String selection = UPGLinksContract.Columns.UPG_LINK_USER_ID + " = ? AND " + UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID + " = ?";
                            Log.d(TAG, "onPositiveDialogResult: selection -> " + selection);
                            String[] selectionArgs = {String.valueOf(user.getId()), String.valueOf(mPostGroup.getId())};
                            cursor = resolver.query(UPGLinksContract.CONTENT_URI, null, selection, selectionArgs, null);
                            if (cursor != null && cursor.getCount() <= 0) {
                                contentValues = new ContentValues();
                                contentValues.put(UPGLinksContract.Columns.UPG_LINK_USER_ID, user.getId());
                                contentValues.put(UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID, mPostGroup.getId());
                                resolver.insert(UPGLinksContract.CONTENT_URI, contentValues);
                                cursor.close();
                            }
                        }
                        Log.d(TAG, "onPositiveDialogResult: POST ends");
                    } else if (mGroupType == GROUP.USER) {
                        Log.d(TAG, "onPositiveDialogResult: USER starts");

                        long id = mUserGroup.getId();
                        contentValues = new ContentValues();
                        contentValues.put(UserGroupsContract.Columns.USER_GROUP_TITLE, mGroupTitle.getText().toString());
                        getContentResolver().update(UserGroupsContract.buildUserGroupUri(id), contentValues, null, null);

                        Log.d(TAG, "onPositiveDialogResult: USER ends");
                    }
                    Log.d(TAG, "onPositiveDialogResult: EDIT ends");
                }
                break;
            case R.id.action_group_cancel:
                Log.d(TAG, "onOptionsItemSelected: CANCEL CLICKED");
                if (mPurposeType == PURPOSE.CREATE) {
                    if (mGroupTitle.length() > 0 || mUsersList.size() > 0) {
                        AppDialog dialog = new AppDialog();
                        Bundle args = new Bundle();
                        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL);
                        args.putString(AppDialog.DIALOG_MESSAGE, "This new group data will be lost.\nDo you really want to cancel?");
                        dialog.setArguments(args);
                        dialog.show(getSupportFragmentManager(), null);
                    } else {
                        finish();
                    }
                    break;
                } else {
                    if (!mGroupTitle.getText().toString().equals(mPostGroup.getTitle())) {
                        AppDialog dialog = new AppDialog();
                        Bundle args = new Bundle();
                        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL);
                        args.putString(AppDialog.DIALOG_MESSAGE, "This new group data will be lost.\nAre you sure you want to cancel?");
                        dialog.setArguments(args);
                        dialog.show(getSupportFragmentManager(), null);
                    } else {
                        finish();
                    }
                    break;
                }
            case R.id.action_group_delete:
                if (!PostGroup.DefaultPostGroups.isDefaultGroup(mPostGroup)) {
                    Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
//                    resolver.delete(PostGroupsContract.buildPostGroupUri(mPostGroup.getId()), null, null);
//                    resolver.delete(PostLinksContract.CONTENT_URI, where, selectionArgs);
                } else {
                    Toast.makeText(this, "Default Post Group can't be deleted", Toast.LENGTH_SHORT).show();
                }
            default:
                Log.d(TAG, "onOptionsItemSelected: default case");
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: starts");
        Intent intent;
        switch (v.getId()) {
            case R.id.buttonAddUserGroup:
                Log.d(TAG, "onClick: Add UserGroup Clicked");
                intent = new Intent(CreateEditGroupActivity.this, ListActivity.class);
                intent.putExtra(ListActivity.INTENT_LIST_TYPE, UserGroup.class.getSimpleName());
                startActivityForResult(intent, REQUEST_CODE_USER_GROUPS_LIST);
                break;
            case R.id.buttonAddSingleUser:
                Log.d(TAG, "onClick: Add SingleUser Clicked");
                intent = new Intent(CreateEditGroupActivity.this, ListActivity.class);
                intent.putExtra(ListActivity.INTENT_LIST_TYPE, User.class.getSimpleName());
                startActivityForResult(intent, REQUEST_CODE_USERS_LIST);
                break;
            default:
                Log.d(TAG, "onClick: default case");
        }
        Log.d(TAG, "onClick: ends");
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

    public ArrayList<User> queryGroupUsers(Uri uri) {
        Log.d(TAG, "queryGroupUsers: starts");
        ArrayList<User> users = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Log.d(TAG, "queryGroupUsers: mPostGroup -> " + mPostGroup);
        Log.d(TAG, "queryGroupUsers: mUserGroup -> " + mUserGroup);
        String[] selectionArgs = {String.valueOf(mPostGroup.getId()), String.valueOf(mUserGroup.getId())};
        Cursor cursor = resolver.query(uri, null, null, selectionArgs, null);

        if (cursor != null) {
            Log.d(TAG, "queryGroupUsers: count -> " + cursor.getCount());
            while (cursor.moveToNext()) {
                users.add(new User(cursor));
            }
            Log.d(TAG, "queryGroupUsers: users -> " + users);
            cursor.close();
        }

        Log.d(TAG, "queryGroupUsers: returning -> " + users);
        return users;
    }

    public long getGroupId() {
        if (mGroupType == GROUP.POST) {
            return mPostGroup.getId();
        } else {
            return mUserGroup.getId();
        }
    }

    @Override
    public void onClickUnselect(int index) {
        Log.d(TAG, "onClickUnselect: starts");
//        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        ContentResolver resolver = getContentResolver();
        String where = UPGLinksContract.Columns.UPG_LINK_USER_ID + " = ? AND " + UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID + " = ?";
        String[] selectionArgs = {String.valueOf(mUsersList.get(index).getId()), String.valueOf(getGroupId())};
        resolver.delete(UPGLinksContract.CONTENT_URI, where, selectionArgs);
        mUsersList.remove(index);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "onClickUnselect: ends");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_USERS_LIST && resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            mUsersList = (ArrayList<User>) bundle.get(INTENT_GROUP_USER_LIST);
            mAdapter.notifyDataSetChanged();
        }
    }

}
