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
 * Use the {@link MakeCourseAvailableFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeCourseAvailableFragment extends Fragment {

    private EditText instructorEditText;
    private EditText deadlineEditText;
    private EditText startDateEditText;
    private EditText scheduleEditText;
    private EditText venueEditText;
    private Spinner courseIdSpinner;
    private Button submitButton;

    public MakeCourseAvailableFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_course_available, container, false);

        courseIdSpinner = view.findViewById(R.id.spinner_course_id2);
        instructorEditText = view.findViewById(R.id.editText_instructor);
        deadlineEditText = view.findViewById(R.id.editText_deadline);
        startDateEditText = view.findViewById(R.id.editText_start_date);
        scheduleEditText = view.findViewById(R.id.editText_schedule);
        venueEditText = view.findViewById(R.id.editText_venue);
        submitButton = view.findViewById(R.id.button_submit_make_available);

        populateCourseSpinner();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = getSelectedCourseId();
                String instructor = instructorEditText.getText().toString();
                String deadline = deadlineEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String schedule = scheduleEditText.getText().toString();
                String venue = venueEditText.getText().toString();

                insertCourseAvailable(courseId,instructor,deadline,startDate,schedule,venue);

            }
        });

        return view;
    }

    private void insertCourseAvailable(String courseID, String instructor, String deadline, String startDate, String schedule, String venue) {
        if (courseID.isEmpty() || instructor.isEmpty() || deadline.isEmpty() || startDate.isEmpty() || schedule.isEmpty() || venue.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkCourseExists(courseID)){
            Toast.makeText(getActivity(), "Course with ID "+courseID+" does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        dbHelper.makeCourseAvailable(courseID,instructor,deadline,startDate,schedule,venue);

        Toast.makeText(getActivity(), "Course with ID:"+courseID + " made available successfully", Toast.LENGTH_SHORT).show();
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
