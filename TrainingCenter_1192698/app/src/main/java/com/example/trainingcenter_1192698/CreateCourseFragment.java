package com.example.trainingcenter_1192698;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateCourseFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCourseFragment extends Fragment {

    private EditText titleEditText;
    private EditText topicsEditText;
    private EditText prerequisitesEditText;
    private Button submitButton;

    public CreateCourseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_course, container, false);

        titleEditText = view.findViewById(R.id.editText_title);
        topicsEditText = view.findViewById(R.id.editText_topics);
        prerequisitesEditText = view.findViewById(R.id.editText_prerequisites);
        submitButton = view.findViewById(R.id.button_submit_create_course);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String topics = topicsEditText.getText().toString();
                String prerequisites = prerequisitesEditText.getText().toString();

                insertCourse(title, topics, prerequisites);
            }
        });

        return view;
    }

    private void insertCourse(String title, String topics, String prerequisites) {
        if (title.isEmpty() || topics.isEmpty() || prerequisites.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        dbHelper.insertCourse(title, topics, prerequisites, null);

        Toast.makeText(getActivity(), title + " course added successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}