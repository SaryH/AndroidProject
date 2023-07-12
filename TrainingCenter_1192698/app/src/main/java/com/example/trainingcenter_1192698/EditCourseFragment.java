package com.example.trainingcenter_1192698;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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
 * Use the {@link EditCourseFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCourseFragment extends Fragment {

    private Spinner courseIdSpinner;
    private EditText newTitleEditText;
    private EditText newTopicsEditText;
    private EditText newPrerequisitesEditText;
    private Button submitButton;

    public EditCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_course, container, false);

        courseIdSpinner = view.findViewById(R.id.spinner_course_id);  // Initialize Spinner
        newTitleEditText = view.findViewById(R.id.editText_new_title);
        newTopicsEditText = view.findViewById(R.id.editText_new_topics);
        newPrerequisitesEditText = view.findViewById(R.id.editText_new_prerequisites);
        submitButton = view.findViewById(R.id.button_submit_edit_course);

        populateCourseSpinner();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = getSelectedCourseId();

                String newTitle = newTitleEditText.getText().toString();
                String newTopics = newTopicsEditText.getText().toString();
                String newPrerequisites = newPrerequisitesEditText.getText().toString();

                updateCourse(courseId, newTitle, newTopics, newPrerequisites);
            }
        });

        return view;
    }

    private void updateCourse(String courseId, String newTitle, String newTopics, String newPrerequisites) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        if(!courseId.isEmpty() && newTitle.isEmpty() && newTopics.isEmpty() && newPrerequisites.isEmpty()){
            if(!checkCourseExists(courseId)){
                Toast.makeText(getActivity(), "Course with ID "+courseId+" does not exist!", Toast.LENGTH_SHORT).show();
                return;
            }
            dbHelper.removeCourse(courseId);
            Toast.makeText(getActivity(), "Course with ID="+courseId+" removed successfully", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (courseId.isEmpty() || newTitle.isEmpty() || newTopics.isEmpty() || newPrerequisites.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkCourseExists(courseId)){
            Toast.makeText(getActivity(), "Course with ID "+courseId+" does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.updateCourse(courseId, newTitle, newTopics, newPrerequisites);

        Toast.makeText(getActivity(), "Course updated successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private boolean checkCourseExists(String courseId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean courseExists = dbHelper.courseExists(db, courseId);
        db.close();
        return courseExists;
    }

    private void populateCourseSpinner() {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, title FROM course", null);
        List<String> courseList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String courseId = cursor.getString(cursor.getColumnIndex("id"));
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