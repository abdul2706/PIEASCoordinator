package com.example.pieascoordinator.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pieascoordinator.R;
import com.example.pieascoordinator.database.UsersContract;
import com.example.pieascoordinator.datatypes.User;
import com.example.pieascoordinator.utilities.ObjectSerializer;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String KEEP_ME_LOG_IN = "keep_me_log_in";

    public Button mButtonLogin;
    public EditText mEditTextUsername, mEditTextPassword;
    public CheckBox mCheckBox;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mEditTextUsername = findViewById(R.id.editTextUsername);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mCheckBox = findViewById(R.id.checkBox);
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if(username.equals("")) {
            Toast.makeText(this, "Username Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")) {
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user;
        ContentResolver contentResolver = getContentResolver();
        String selection = UsersContract.Columns.USER_USERNAME + " = ? AND " + UsersContract.Columns.USER_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = contentResolver.query(UsersContract.CONTENT_URI, null, selection, selectionArgs, null);
        Log.d(TAG, "onClick: cursor -> " + cursor);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            Log.d(TAG, "onClick: login successful");
            Toast.makeText(getApplicationContext(), "User identified, Logging In.......", Toast.LENGTH_SHORT).show();

            user = new User(
                    cursor.getInt(cursor.getColumnIndex(UsersContract.Columns._ID)),
                    cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_DEPARTMENT)),
                    cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_BATCH))
            );
            Log.d(TAG, "onClick: user -> " + user);

            try {
                Log.d(TAG, "onClick: mCheckBox -> " + mCheckBox.isChecked());
                if (mCheckBox.isChecked()) {
                    sharedPreferences.edit().putBoolean(KEEP_ME_LOG_IN, true).apply();
                    sharedPreferences.edit().putString(CURRENT_USER, ObjectSerializer.serialize(user)).apply();
                } else {
                    sharedPreferences.edit().putBoolean(KEEP_ME_LOG_IN, false).apply();
                    sharedPreferences.edit().remove(CURRENT_USER).apply();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Log.d(TAG, "onClick: starting MainActivity");
                intent.putExtra(CURRENT_USER, user);
                startActivity(intent);
                Log.d(TAG, "onClick: finishing LoginActivity");
                finish();
            } catch (IOException e) {
                Log.e(TAG, "onClick: e -> " + e.getMessage(), e);
                Toast.makeText(LoginActivity.this, "Login Failed Try Again", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "onClick: e -> " + e.getMessage(), e);
                Toast.makeText(LoginActivity.this, "Login Failed Try Again", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            Log.d(TAG, "onClick: Wrong User Name or Password");
            Toast.makeText(LoginActivity.this, "Wrong User Name or Password, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

}
