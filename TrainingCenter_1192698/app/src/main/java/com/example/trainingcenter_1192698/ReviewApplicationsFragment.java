package com.example.trainingcenter_1192698;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewApplicationsFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ReviewApplicationsFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_applications, container, false);
        dbHelper = new DatabaseHelper(getContext());

        displayCourseApplications(view);

        return view;
    }

    private void displayCourseApplications(View view) {
        layout = view.findViewById(R.id.layout_course_applications);
        Cursor cursor = dbHelper.getAllCourseApplications();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String applicationId = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String courseId = cursor.getString(cursor.getColumnIndex("course_id"));
                String pre=dbHelper.getCoursePrerequisites(courseId);
                @SuppressLint("Range") String traineeID = cursor.getString(cursor.getColumnIndex("student_id"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));

                View courseApplicationView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_review_applications, layout, false);
                TextView applicationIdTextView = courseApplicationView.findViewById(R.id.text_application_id);
                TextView courseIdTextView = courseApplicationView.findViewById(R.id.text_course_id);
                TextView courseIPre = courseApplicationView.findViewById(R.id.text_course_pre);
                TextView traineeIDTextView = courseApplicationView.findViewById(R.id.text_student_id);
                Button acceptButton = courseApplicationView.findViewById(R.id.button_accept);
                Button denyButton = courseApplicationView.findViewById(R.id.button_deny);

                applicationIdTextView.setText("Application ID: " + applicationId);
                courseIdTextView.setText("Course ID: " + courseId);
                courseIPre.setText("Prerequisites: " + pre);
                traineeIDTextView.setText("Student ID: " + traineeID);

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Trainee "+traineeID+" accepted",Toast.LENGTH_SHORT).show();
                        dbHelper.processCourseApplication(Integer.parseInt(applicationId),traineeID,Integer.parseInt(courseId),true);
                        layout.removeView(courseApplicationView);
                    }
                });

                denyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Trainee "+traineeID+" rejected",Toast.LENGTH_SHORT).show();
                        dbHelper.processCourseApplication(Integer.parseInt(applicationId),traineeID,Integer.parseInt(courseId),false);
                        layout.removeView(courseApplicationView);
                    }
                });

                layout.addView(courseApplicationView);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

}
