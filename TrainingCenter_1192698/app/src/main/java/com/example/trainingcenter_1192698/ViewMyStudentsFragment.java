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
 * Use the {@link ViewMyStudentsFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMyStudentsFragment extends Fragment {

    private TextView myStudents;

    ViewMyStudentsFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_my_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myStudents = view.findViewById(R.id.TextView_view_my_students);
        Bundle arguments = getArguments();
        String instructorEmail = arguments.getString("instructorEmail");
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        String data = databaseHelper.getInstructorTrainees(instructorEmail);
        myStudents.setText(data);

    }
}