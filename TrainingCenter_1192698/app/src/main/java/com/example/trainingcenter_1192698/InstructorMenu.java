package com.example.trainingcenter_1192698;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);
        //Greet the instructor
        Intent intent = getIntent();
        String instructorEmail= intent.getStringExtra("instructorEmail");
        Toast.makeText(this, "Welcome Instructor:"+instructorEmail+"!", Toast.LENGTH_SHORT).show();

        TextView emailTextView = findViewById(R.id.LoggedInAs_Instructor);
        emailTextView.setText("Logged in as:["+instructorEmail+"]");

        Button logoutInstructor = findViewById(R.id.button_logout_instructor);
        logoutInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InstructorMenu.this, "Goodbye "+instructorEmail+"!", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(InstructorMenu.this, MainActivity.class);
                startActivity(home);
            }
        });

        Button viewSched = findViewById(R.id.button_view_schedule);
        viewSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewScheduleFragment viewScheduleFragment = new ViewScheduleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("instructorEmail", instructorEmail);
                viewScheduleFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view_schedule, viewScheduleFragment);
                fragmentTransaction.commit();
            }
        });

        Button viewStudents = findViewById(R.id.button_view_my_students);
        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewMyStudentsFragment viewMyStudentsFragment = new ViewMyStudentsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("instructorEmail", instructorEmail);
                viewMyStudentsFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view_my_students, viewMyStudentsFragment);
                fragmentTransaction.commit();
            }
        });

        Button editProfile = findViewById(R.id.button_edit_profile_instructor);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInstructorProfileFragment editInstructorProfileFragment = new EditInstructorProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("instructorEmail", instructorEmail);
                editInstructorProfileFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_edit_profile_instructor, editInstructorProfileFragment);
                fragmentTransaction.commit();
            }
        });

    }
}