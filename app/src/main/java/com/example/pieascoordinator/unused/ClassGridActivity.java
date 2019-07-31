//package com.example.pieascoordinator.activities;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.pieascoordinator.R;
//import com.example.pieascoordinator.Unused.GridAdapter;
//
//public class ClassGridActivity extends AppCompatActivity {
//
//    private static final String TAG = "ClassGridActivity";
//    public static String USERNAME = "";
//
//    public GridView gridView;
//    public String[] classNames, temp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.z_activity_class_grid);
//        classNames = OurDatabase.getSubjectsNamesOfThisUser(USERNAME);
//        if (OurDatabase.isTeacher(USERNAME)) {
//            temp = new String[classNames.length + 3];
//            temp[0] = "BSCS 16-20";
//            temp[1] = "BSCS 17-21";
//            temp[2] = "BSCS 18-22";
//            for (int i = 3; i < temp.length; i++) {
//                temp[i] = classNames[i - 3];
//            }
//        } else {
//            temp = new String[classNames.length + 1];
//            temp[0] = OurDatabase.getBatchOfThisStudent(USERNAME);
//            for (int i = 1; i < temp.length; i++) {
//                temp[i] = classNames[i - 1];
//            }
//        }
//        classNames = temp;
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(USERNAME.toUpperCase());
//            if (LoginActivity.IS_TEACHER) {
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#770000")));
//            } else {
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00cc00")));
//            }
//        }
//
//        gridView = findViewById(R.id.gridView);
//        GridAdapter adapter = new GridAdapter(ClassGridActivity.this, classNames);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ClassGridActivity.this, "Clicked : " + classNames[position], Toast.LENGTH_SHORT).show();
//                LoginActivity.SUBJECT = classNames[position];
//                Intent intent = new Intent(ClassGridActivity.this, PostAddActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//}
