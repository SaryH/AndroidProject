package com.example.trainingcenter_1192698;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCoursesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCoursesFragment extends Fragment {
    MyCoursesFragment(){};
    private TextView myCourses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_courses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myCourses = view.findViewById(R.id.TextView_my_courses); // TextView reference
        Bundle arguments = getArguments();
        String traineeEmail = arguments.getString("traineeEmail");
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        String data = databaseHelper.getRegisteredCoursesForTrainee(traineeEmail);
        myCourses.setText(data);

    }

}