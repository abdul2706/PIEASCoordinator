package com.example.pieascoordinator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static int NUMBER_OF_ATTEMPTS = 0;
    public static String SUBJECT = "";
    public static boolean IS_TEACHER = true;

    public Button mButtonLogin;
    public EditText mEditTextUsername, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = findViewById(R.id.buttonLogin);
        mEditTextUsername = findViewById(R.id.editTextUsername);
        mEditTextPassword = findViewById(R.id.editTextPassword);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEditTextUsername.getText().toString();
                String userPassword = mEditTextPassword.getText().toString();
                if (OurDatabase.isTeacher(userName)) {
                    if (OurDatabase.authenticateTeacher(userName, userPassword) && NUMBER_OF_ATTEMPTS <= 5) {
                        Toast.makeText(LoginActivity.this, "Logged in as Teacher with " + OurDatabase.getSubjectsNamesOfThisTeacher(userName).length + " classes!", Toast.LENGTH_SHORT).show();
                        IS_TEACHER = true;
                        ClassGridActivity.USERNAME = userName;
                        mEditTextUsername.setText("");
                        mEditTextPassword.setText("");
                        NUMBER_OF_ATTEMPTS = 0;
                        gotoClassActivity();
                    } else {
                        if (NUMBER_OF_ATTEMPTS >= 6) {
                            Toast.makeText(LoginActivity.this, "Too many attempts! Try next time", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Password! " + NUMBER_OF_ATTEMPTS++, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (OurDatabase.isStudent(userName)) {
                    if (OurDatabase.authenticateStudent(userName, userPassword) && NUMBER_OF_ATTEMPTS <= 5) {
                        Toast.makeText(LoginActivity.this, "LoginActivity as Student", Toast.LENGTH_SHORT).show();
                        IS_TEACHER = false;
                        ClassGridActivity.USERNAME = userName;
                        mEditTextUsername.setText("");
                        mEditTextPassword.setText("");
                        NUMBER_OF_ATTEMPTS = 0;
                        gotoClassActivity();
                    } else {
                        if (NUMBER_OF_ATTEMPTS >= 6) {
                            Toast.makeText(LoginActivity.this, "Too many attempts! Try next time", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Password! " + NUMBER_OF_ATTEMPTS++, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong User Name or Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void gotoClassActivity() {
        Intent intent = new Intent(this, ClassGridActivity.class);
        startActivity(intent);
    }

}
