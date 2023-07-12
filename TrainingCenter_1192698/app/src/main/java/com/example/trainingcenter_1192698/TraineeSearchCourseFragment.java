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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TraineeSearchCourseFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraineeSearchCourseFragment extends Fragment {

    public TraineeSearchCourseFragment() {}
    private EditText courseNameEditText;
    private Button searchButton;
    private TextView courseInfoTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainee_search_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseNameEditText = view.findViewById(R.id.editText_search_course_name);
        searchButton = view.findViewById(R.id.button_trainee_search_course);
        courseInfoTextView = view.findViewById(R.id.textView_course_info);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                String courseName = courseNameEditText.getText().toString();
                String info=databaseHelper.searchCourseByName(courseName);
                courseInfoTextView.setText(info);
            }
        });
    }

}