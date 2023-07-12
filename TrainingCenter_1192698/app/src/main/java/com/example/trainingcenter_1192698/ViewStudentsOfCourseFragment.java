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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewStudentsOfCourseFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewStudentsOfCourseFragment extends Fragment {

    private Spinner courseIdSpinner;
    private Button submitButton;
    public ViewStudentsOfCourseFragment(){};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_students_of_course, container, false);

        courseIdSpinner = view.findViewById(R.id.spinner_course_id_view_students);
        submitButton = view.findViewById(R.id.button_view_students_submit);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        populateCourseSpinner();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = getSelectedCourseId();
                ArrayList<String> temp = databaseHelper.getStudentsByCourseId(courseId);

                StringBuilder stringBuilder = new StringBuilder();
                int count=1;
                for (String student : temp) {
                    stringBuilder.append(String.valueOf(count++)).append(". ").append(student).append("\n");
                }

                String studentsText = stringBuilder.toString().trim();
                TextView textViewStudents = getView().findViewById(R.id.text_view_students);
                textViewStudents.setText(studentsText);
                //Toast.makeText(getContext(),"Application Submitted for course ID:"+courseId,Toast.LENGTH_SHORT).show();
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