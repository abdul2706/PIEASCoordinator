package com.example.pieascoordinator.datatypes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.pieascoordinator.database.UserLinksContract;
import com.example.pieascoordinator.database.UsersContract;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final String TAG = "User";

    private long _ID;
    private String mUsername;
    private String mPassword;
    private String mDepartment;
    private String mBatch;

    public User() {
        this(-1, "Anonymous", "", "Unknown", "Unknown");
    }

    public User(long id, String username, String password, String department, String batch) {
        Log.d(TAG, "User: New User Created");
        this._ID = id;
        this.mUsername = username;
        this.mPassword = password;
        this.mDepartment = department;
        this.mBatch = batch;
    }

    public User(Cursor cursor) {
        this(
                cursor.getLong(cursor.getColumnIndex(UsersContract.Columns._ID)),
                cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_USERNAME)),
                cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_DEPARTMENT)),
                cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_BATCH))
        );
//        this._ID = cursor.getLong(cursor.getColumnIndex(UsersContract.Columns._ID));
//        this.mUsername = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_USERNAME));
//        this.mPassword = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD));
//        this.mDepartment = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_DEPARTMENT));
//        this.mBatch = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_BATCH));
    }

    public long getId() {
        return _ID;
    }

    public void setId(long id) {
        this._ID = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        this.mDepartment = department;
    }

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        this.mBatch = batch;
    }

    @Override
    public String toString() {
        return "User{" +
                "_ID=" + _ID +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mDepartment='" + mDepartment + '\'' +
                ", mBatch='" + mBatch + '\'' +
                '}';
    }

    public static void insert(Context context, ArrayList<String> usersStringList, ArrayList<PostGroup> postGroups) {
        Log.d(TAG, "insert: starts");

        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues;

        for (String userString : usersStringList) {
            String[] projection = {UsersContract.Columns.USER_USERNAME, UsersContract.Columns.USER_PASSWORD, UsersContract.Columns.USER_DEPARTMENT, UsersContract.Columns.USER_BATCH};
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < projection.length - 1; i++) {
                stringBuilder.append(projection[i]).append(" = ? AND ");
            }
            String selection = stringBuilder.toString() + projection[projection.length - 1] + " = ?";
            Log.d(TAG, "insert: selection -> " + selection);
            String[] selectionArgs = userString.split(",");
            for (int i = 0; i < selectionArgs.length; i++) {
                selectionArgs[i] = selectionArgs[i].trim();
                Log.d(TAG, "insert: str -> " + selectionArgs[i]);
            }

            Cursor cursor = contentResolver.query(UsersContract.CONTENT_URI, null, selection, selectionArgs, null);
            if (cursor != null && cursor.getCount() <= 0) {
                contentValues = new ContentValues();
                for (int i = 0; i < projection.length; i++) {
                    contentValues.put(projection[i], selectionArgs[i]);
                }
                Log.d(TAG, "insertUsers: contentValues -> " + contentValues);
                Uri newUserUri = contentResolver.insert(UsersContract.CONTENT_URI, contentValues);
                long newUserId = UsersContract.getUserId(newUserUri);
                Log.d(TAG, "insertUsers: newUserId -> " + newUserId);

                for(PostGroup postGroup : postGroups) {
                    if(postGroup.getTitle().equals(PostGroup.DefaultPostGroups.ANNOUNCEMENTS)
                    || postGroup.getTitle().equals(PostGroup.DefaultPostGroups.DISCUSSIONS)
                    || postGroup.getTitle().equals(PostGroup.DefaultPostGroups.NEWS)
                    || postGroup.getTitle().equals(PostGroup.DefaultPostGroups.NOTIFICATIONS)) {
                        Log.d(TAG, "insert: postGroup -> " + postGroup);
                        contentValues = new ContentValues();
                        contentValues.put(UserLinksContract.Columns.USER_LINK_USER_GROUP_ID, postGroup.getId());
                        contentValues.put(UserLinksContract.Columns.USER_LINK_USER_ID, newUserId);
                        contentResolver.insert(UserLinksContract.CONTENT_URI, contentValues);
                    }
                }

                cursor.close();
            } else {
                Log.d(TAG, "insert: user already present -> " + userString);
            }
        }

        Log.d(TAG, "insert: ends");
    }

    public static User queryUser(Context context, long userId) {
        User user = new User();
        Cursor cursor = context.getContentResolver().query(UsersContract.buildUserUri(userId), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            user = new User(cursor);
            cursor.close();
        }
        return user;
    }

}


//    public static void insert(Context context) {
//        Log.d(TAG, "insertUsers: starts");
//
//        final String IS_DATA_INSERTED = "is_data_inserted";
//        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
////        sharedPreferences.edit().putBoolean("IS_DATA_INSERTED", false).apply();
//        boolean isDataInserted = sharedPreferences.getBoolean(IS_DATA_INSERTED, false);
//        Log.d(TAG, "insertUsers: isDataInserted -> " + isDataInserted);
//
//        if (!isDataInserted) {
//            ContentResolver contentResolver = context.getContentResolver();
////            contentResolver.delete(UsersContract.CONTENT_URI, null, null);
//            ContentValues contentValues;
//
//            String line;
//            InputStream inputStream = context.getResources().openRawResource(R.raw.users);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//            try {
//                bufferedReader.readLine();  // ignore header line in csv file which contains columns names
//                while ((line = bufferedReader.readLine()) != null) {
//                    String[] values = line.split(",");
//                    contentValues = new ContentValues();
//                    contentValues.put(UsersContract.Columns.USER_USERNAME, values[0].trim());
//                    contentValues.put(UsersContract.Columns.USER_PASSWORD, values[1].trim());
//                    contentValues.put(UsersContract.Columns.USER_DEPARTMENT, values[2].trim());
//                    contentValues.put(UsersContract.Columns.USER_BATCH, values[3].trim());
//                    Log.d(TAG, "insertUsers: contentValues -> " + contentValues);
//                    Uri uri = contentResolver.insert(UsersContract.CONTENT_URI, contentValues);
//                    Log.d(TAG, "insertUsers: uri -> " + uri);
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "insertUsers: e -> " + e.getMessage(), e);
//            } finally {
//                sharedPreferences.edit().putBoolean(IS_DATA_INSERTED, true).apply();
//                try {
//                    bufferedReader.close();
//                    inputStream.close();
//                } catch (Exception e) {
//                    Log.e(TAG, "insertUsers: e -> " + e.getMessage(), e);
//                }
//            }
//        }
//        Log.d(TAG, "insertUsers: ends");
//    }
