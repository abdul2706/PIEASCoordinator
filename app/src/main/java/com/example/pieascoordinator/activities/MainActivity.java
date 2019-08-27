package com.example.pieascoordinator.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.adapters.CursorPostsAdapter;
import com.example.pieascoordinator.database.PostGroupsContract;
import com.example.pieascoordinator.database.PostLinksContract;
import com.example.pieascoordinator.database.PostsContract;
import com.example.pieascoordinator.database.UsersContract;
import com.example.pieascoordinator.datatypes.Post;
import com.example.pieascoordinator.datatypes.PostGroup;
import com.example.pieascoordinator.datatypes.User;
import com.example.pieascoordinator.datatypes.UserGroup;
import com.example.pieascoordinator.utilities.ObjectSerializer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Environment;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        CursorPostsAdapter.OnPostClickListener {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CODE_READ_STORAGE = 0;

    public static final int LOADER_POST_ID = 0;
    public static final String ADMIN_REGISTERED = "admin_registered";

    public static User CURRENT_USER;
    public static PostGroup CURRENT_POST_GROUP;
    public static ArrayList<PostGroup> mPostGroups = new ArrayList<>();
    public static ArrayList<UserGroup> mUserGroups = new ArrayList<>();

    private SharedPreferences mSharedPreferences;
    private CursorPostsAdapter mPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        PostGroup.DefaultPostGroups.insert(this);
        adminLogIn();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fabAddPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onOptionsItemSelected: Add Post Clicked");
                Intent intent = new Intent(MainActivity.this, PostAddActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setupNavigation();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostAdapter = new CursorPostsAdapter(this, null, this);
        recyclerView.setAdapter(mPostAdapter);

        CURRENT_POST_GROUP = mPostGroups.get(0);
        setTitle(CURRENT_POST_GROUP.getTitle());
        getSupportLoaderManager().initLoader(LOADER_POST_ID, null, this);

        Log.d(TAG, "onCreate: ends");
    }

    public void setupNavigation() {
        Log.d(TAG, "setupNavigation: starts");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu navigationMenu = navigationView.getMenu();

        Cursor postGroupsCursor = getContentResolver().query(PostGroupsContract.CONTENT_URI, null, null, null, null);
        if (postGroupsCursor != null) {
            SubMenu subMenu = navigationMenu.addSubMenu(PostGroup.MENU_NAME);
            while (postGroupsCursor.moveToNext()) {
                PostGroup postGroup = new PostGroup(postGroupsCursor);
                subMenu.add(0, (int) postGroup.getId(), 0, postGroup.getTitle());
                mPostGroups.add(postGroup);
            }
            postGroupsCursor.close();
        }

        getMenuInflater().inflate(R.menu.activity_main_drawer, navigationMenu);
        drawer.closeDrawers();

        View view = navigationView.getHeaderView(0);
        TextView navBarUsername = view.findViewById(R.id.navbarUsername);
        TextView navBarDepartmentBatch = view.findViewById(R.id.navbarDepartmentBatch);
        navBarUsername.setText(CURRENT_USER.getUsername());
        String temp = CURRENT_USER.getDepartment() + " : " + CURRENT_USER.getBatch();
        navBarDepartmentBatch.setText(temp);
    }

    public void adminLogIn() {
        Log.d(TAG, "adminLogIn: starts");
//        getContentResolver().delete(UsersContract.CONTENT_URI, null, null);
//        mSharedPreferences.edit().remove(ADMIN_REGISTERED).apply();

        boolean adminRegistered = mSharedPreferences.getBoolean(ADMIN_REGISTERED, false);
        if (!adminRegistered) {
            Log.d(TAG, "adminLogIn: registering admin");
            ContentValues contentValues = new ContentValues();
            contentValues.put(UsersContract.Columns.USER_USERNAME, "Admin");
            contentValues.put(UsersContract.Columns.USER_PASSWORD, "Admin");
            contentValues.put(UsersContract.Columns.USER_DEPARTMENT, "Admin");
            contentValues.put(UsersContract.Columns.USER_BATCH, "Admin");
            getContentResolver().insert(UsersContract.CONTENT_URI, contentValues);
            mSharedPreferences.edit().putBoolean(ADMIN_REGISTERED, true).apply();
            Log.d(TAG, "adminLogIn: admin registered");
        }
        Cursor cursor = getContentResolver().query(UsersContract.CONTENT_URI, null, UsersContract.Columns.USER_USERNAME + " = ?", new String[]{"Admin"}, null);
        cursor.moveToFirst();
        CURRENT_USER = new User(cursor);
        cursor.close();
        Log.d(TAG, "adminLogIn: ends");
    }

    public void checkLogIn() {
        Log.d(TAG, "checkLogIn: starts");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            CURRENT_USER = (User) bundle.get(LoginActivity.CURRENT_USER);
            Log.d(TAG, "checkLogIn: CURRENT_USER -> " + CURRENT_USER);
            if (CURRENT_USER != null) {
                return;
            }
        }

        boolean keepMeLogIn = mSharedPreferences.getBoolean(LoginActivity.KEEP_ME_LOG_IN, false);
        if (keepMeLogIn) {
            // user already logged in so take out credentials from mSharedPreferences
            Log.d(TAG, "checkLogIn: keepMeLogIn true");
            try {
                CURRENT_USER = (User) ObjectSerializer.deserialize(mSharedPreferences.getString(LoginActivity.CURRENT_USER, ""));
            } catch (IOException e) {
                Log.e(TAG, "checkLogIn: e -> " + e.getMessage(), e);
            } catch (Exception e) {
                Log.e(TAG, "checkLogIn: e -> " + e.getMessage(), e);
            }
        } else {
            // user not logged in so goto login activity for credentials
            Log.d(TAG, "checkLogIn: keepMeLogIn false");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Log.d(TAG, "checkLogIn: starting LoginActivity");
            startActivity(intent);
            Log.d(TAG, "checkLogIn: finishing MainActivity");
            finish();
        }
        Log.d(TAG, "checkLogIn: ends");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starts");

        Intent intent;
        switch (item.getItemId()) {
//            case R.id.action_create_post:
//                Log.d(TAG, "onOptionsItemSelected: Add Post Clicked");
//                intent = new Intent(MainActivity.this, PostAddActivity.class);
//                startActivity(intent);
//                break;
            case R.id.action_edit_post_group:
                Log.d(TAG, "onOptionsItemSelected: Rename Post Group Clicked");
                intent = new Intent(MainActivity.this, CreateEditGroupActivity.class);
                intent.putExtra(CreateEditGroupActivity.GROUP_TYPE, CreateEditGroupActivity.GROUP.POST);
                intent.putExtra(CreateEditGroupActivity.PURPOSE_TYPE, CreateEditGroupActivity.PURPOSE.EDIT);
                intent.putExtra(PostGroup.class.getSimpleName(), CURRENT_POST_GROUP);
                startActivity(intent);
                break;
            case R.id.action_create_post_group:
                Log.d(TAG, "onOptionsItemSelected: Add Post Group Clicked");
                intent = new Intent(MainActivity.this, CreateEditGroupActivity.class);
                intent.putExtra(CreateEditGroupActivity.GROUP_TYPE, CreateEditGroupActivity.GROUP.POST);
                intent.putExtra(CreateEditGroupActivity.PURPOSE_TYPE, CreateEditGroupActivity.PURPOSE.CREATE);
                startActivity(intent);
                break;
            case R.id.action_create_user_group:
                intent = new Intent(MainActivity.this, CreateEditGroupActivity.class);
                intent.putExtra(CreateEditGroupActivity.GROUP_TYPE, CreateEditGroupActivity.GROUP.USER);
                intent.putExtra(CreateEditGroupActivity.PURPOSE_TYPE, CreateEditGroupActivity.PURPOSE.CREATE);
                startActivity(intent);
                break;
//            case R.id.action_add_users:
//                Log.d(TAG, "onOptionsItemSelected: Add Users Clicked");
//                if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_READ_STORAGE);
//                } else {
//                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, PERMISSION_CODE_READ_STORAGE);
//                }
//                break;
//            case R.id.action_logout:
//                Log.d(TAG, "onOptionsItemSelected: Logout Clicked");
//                mSharedPreferences.edit().remove(LoginActivity.KEEP_ME_LOG_IN).apply();
//                mSharedPreferences.edit().remove(LoginActivity.CURRENT_USER).apply();
//                intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: default case");
                return super.onOptionsItemSelected(item);
        }

        Log.d(TAG, "onOptionsItemSelected: ends");
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: id -> " + item.getItemId());

        switch (item.getItemId()) {
            case R.id.menu_show_all_users:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_import_users_from_csv:
                Log.d(TAG, "onOptionsItemSelected: Add Users Clicked");
                if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_READ_STORAGE);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, PERMISSION_CODE_READ_STORAGE);
                }
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                CURRENT_POST_GROUP = mPostGroups.get(item.getItemId() - 1);
                setTitle(CURRENT_POST_GROUP.getTitle());
                getSupportLoaderManager().destroyLoader(LOADER_POST_ID);
                getSupportLoaderManager().initLoader(LOADER_POST_ID, null, this);
                break;

//            case R.id.nav_home:
//                Log.d(TAG, "onNavigationItemSelected: Home Clicked");
//                break;
//            case R.id.nav_gallery:
//                Log.d(TAG, "onNavigationItemSelected: Gallery Clicked");
//                break;
//            case R.id.nav_slideshow:
//                Log.d(TAG, "onNavigationItemSelected: Slideshow Clicked");
//                break;
//            case R.id.nav_tools:
//                Log.d(TAG, "onNavigationItemSelected: Tools Clicked");
//                break;
//            case R.id.nav_share:
//                Log.d(TAG, "onNavigationItemSelected: Share Clicked");
//                break;
//            case R.id.nav_send:
//                Log.d(TAG, "onNavigationItemSelected: Send Clicked");
//                break;
            default:
                Log.d(TAG, "onNavigationItemSelected: default case");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    public ArrayList<String> loadUsersFromUri(Context context, Uri fileUri) {
        ArrayList<String> userStringList = new ArrayList<>();
        Scanner fileReader;
        try {
            String pathSegment = fileUri.getLastPathSegment();
            Log.d(TAG, "loadUsersFromUri: pathSegment -> " + pathSegment);
            pathSegment = pathSegment.substring(pathSegment.indexOf(':') + 1);
            Log.d(TAG, "loadUsersFromUri: pathSegment -> " + pathSegment);
            File file = new File(Environment.getExternalStorageDirectory(), pathSegment);
            Log.d(TAG, "loadUsersFromUri: file -> " + file);
            if (!file.exists()) {
                Toast.makeText(context, "Error Opening File", Toast.LENGTH_SHORT).show();
            }
            fileReader = new Scanner(file);
            fileReader.nextLine();
            while (fileReader.hasNext()) {
                String temp = fileReader.nextLine();
                Log.d(TAG, "loadUsersFromUri: temp -> " + temp);
                userStringList.add(temp);
            }
            fileReader.close();
        } catch (IOException e) {
            Log.d(TAG, "loadFile: e.getMessage() -> " + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "loadUsersFromUri: userStringList -> " + userStringList);
        return userStringList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CODE_READ_STORAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            Log.d(TAG, "onActivityResult: uri -> " + uri);
            ArrayList<String> usersArrayList = loadUsersFromUri(this, uri);
            User.insert(this, usersArrayList, mPostGroups);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader() called with: id = [" + id + "]");
        String[] selectionArgs = {String.valueOf(CURRENT_POST_GROUP.getId())};
        String sortOrder = PostsContract.Columns.POST_DATETIME;
        Log.d(TAG, "onCreateLoader: selectionsArgs -> " + selectionArgs[0]);
        Log.d(TAG, "onCreateLoader: sortOrder -> " + sortOrder);

        switch (id) {
            case LOADER_POST_ID:
                return new CursorLoader(this, PostLinksContract.CONTENT_URI, null, null, selectionArgs, sortOrder);
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

    @Override
    public void onPostClick(Post post) {
        Log.d(TAG, "onPostClick: starts");
        Log.d(TAG, "onPostClick: post -> " + post);
        Toast.makeText(this, "Post is clicked with id -> " + post.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, PostViewActivity.class);
        intent.putExtra(Post.class.getSimpleName(), post);
        startActivity(intent);
    }

}
