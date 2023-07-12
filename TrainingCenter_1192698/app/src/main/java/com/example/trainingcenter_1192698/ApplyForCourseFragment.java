package com.example.trainingcenter_1192698;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplyForCourseFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyForCourseFragment extends Fragment {

    public ApplyForCourseFragment() {}

    private Spinner courseIdSpinner;
    private Button submitButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_for_course, container, false);

        Bundle arguments = getArguments();
        String traineeEmail = arguments.getString("traineeEmail");

        courseIdSpinner = view.findViewById(R.id.spinner_course_id_apply);
        submitButton = view.findViewById(R.id.button_submit_application);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        populateCourseSpinner();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = getSelectedCourseId();
                databaseHelper.insertCourseApplication(courseId,traineeEmail);
                Toast.makeText(getContext(),"Application Submitted for course ID:"+courseId,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void populateCourseSpinner() {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT course_id, title FROM courses_available " +
                "INNER JOIN course ON courses_available.course_id = course.id", null);
        List<String> courseList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String courseId = cursor.getString(cursor.getColumnIndex("course_id"));
                @SuppressLint("Range") String courseTitle = cursor.getString(cursor.getColumnIndex("title"));
                String courseText = courseId + " - " + courseTitle;
                courseList.add(courseText);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseIdSpinner.setAdapter(adapter);
    }

    private String getSelectedCourseId() {
        String selectedCourseText = courseIdSpinner.getSelectedItem().toString();
        String[] tokens = selectedCourseText.split(" - ");
        return tokens[0];
    }

}