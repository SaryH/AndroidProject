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

public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        //Greet the admin
        Intent intent = getIntent();
        String adminEmail= intent.getStringExtra("adminEmail");
        Toast.makeText(this, "Welcome Admin:"+adminEmail+"!", Toast.LENGTH_SHORT).show();

        TextView emailTextView = findViewById(R.id.LoggedInAs_Admin);
        emailTextView.setText("Logged in as:["+adminEmail+"]");

        Button logoutAdmin = findViewById(R.id.button_logout_admin);
        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminMenu.this, "Goodbye "+adminEmail+"!", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(AdminMenu.this, MainActivity.class);
                startActivity(home);
            }
        });

        Button createCourseButton = findViewById(R.id.button_create_course);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the CreateCourseFragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CreateCourseFragment());
                fragmentTransaction.commit();
            }
        });
        Button buttonEditCourse = findViewById(R.id.button_edit_course);
        buttonEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCourseFragment editCourseFragment = new EditCourseFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container2, editCourseFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button buttonMakeCourseAvailable = findViewById(R.id.button_course_available);
        buttonMakeCourseAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCourseAvailableFragment courseAvailableFragment = new MakeCourseAvailableFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container3, courseAvailableFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button buttonViewStudentsOfCourse = findViewById(R.id.button_view_students_of_course);
        buttonViewStudentsOfCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewStudentsOfCourseFragment viewStudentsOfCourseFragment = new ViewStudentsOfCourseFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_students_of_course, viewStudentsOfCourseFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button reviewApplications = findViewById(R.id.button_accept_reject);
        reviewApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewApplicationsFragment reviewApplicationsFragment = new ReviewApplicationsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_review_applications, reviewApplicationsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button viewProfile = findViewById(R.id.button_view_users);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUserProfileFragment viewUserProfileFragment = new ViewUserProfileFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_profile, viewUserProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}