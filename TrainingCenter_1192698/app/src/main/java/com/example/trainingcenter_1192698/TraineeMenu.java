package com.example.trainingcenter_1192698;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TraineeMenu extends AppCompatActivity {

    private static final String CHANNEL_ID = "TraineeNotifications";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_menu);

        Intent intent = getIntent();
        String traineeEmail= intent.getStringExtra("traineeEmail");
        Toast.makeText(this, "Welcome Trainee:"+traineeEmail+"!", Toast.LENGTH_SHORT).show();

        TextView emailTextView = findViewById(R.id.LoggedInAs_Trainee);
        emailTextView.setText("Logged in as:["+traineeEmail+"]");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        showNotifications(databaseHelper.getTraineeNotifications(traineeEmail));

        Button logoutTrainee = findViewById(R.id.button_logout_trainee);
        logoutTrainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TraineeMenu.this, "Goodbye "+traineeEmail+"!", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(TraineeMenu.this, MainActivity.class);
                startActivity(home);
            }
        });

        Button applyForCourseButton = findViewById(R.id.button_apply_for_course);
        applyForCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyForCourseFragment applyforcourseFragment = new ApplyForCourseFragment();
                Bundle bundle = new Bundle();
                bundle.putString("traineeEmail", traineeEmail);
                applyforcourseFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_apply_for_course, applyforcourseFragment);
                fragmentTransaction.commit();
            }
        });

        Button searchCourse = findViewById(R.id.button_search_course);
        searchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TraineeSearchCourseFragment searchCourseFragment = new TraineeSearchCourseFragment();
                Bundle bundle = new Bundle();
                bundle.putString("traineeEmail", traineeEmail);
                searchCourseFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_search_for_course, searchCourseFragment);
                fragmentTransaction.commit();
            }
        });

        Button myCourses = findViewById(R.id.button_my_courses);
        myCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCoursesFragment myCoursesFragment = new MyCoursesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("traineeEmail", traineeEmail);
                myCoursesFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_my_courses, myCoursesFragment);
                fragmentTransaction.commit();
            }
        });

        Button editProfile = findViewById(R.id.button_edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("traineeEmail", traineeEmail);
                editProfileFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view_schedule, editProfileFragment);
                fragmentTransaction.commit();
            }
        });
    }
    private void showNotifications(ArrayList<String> notifications) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Trainee Notifications", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Trainee Notifications");
        channel.enableLights(true);
        notificationManager.createNotificationChannel(channel);

        for (int i = 0; i < notifications.size(); i++) {
            String notificationText = notifications.get(i);

            int notificationId = NOTIFICATION_ID + i;

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notif)
                    .setContentTitle("Trainee Notification")
                    .setContentText(notificationText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Notification notification = builder.build();
            notificationManager.notify(notificationId, notification);
        }
    }

}