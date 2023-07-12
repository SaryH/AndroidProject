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
 * Use the {@link ViewScheduleFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewScheduleFragment extends Fragment {

    private TextView mySchedule;

    ViewScheduleFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mySchedule = view.findViewById(R.id.TextView_view_schedule);
        Bundle arguments = getArguments();
        String instructorEmail = arguments.getString("instructorEmail");
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        String data = databaseHelper.getInstructorSchedule(instructorEmail);
        mySchedule.setText(data);

    }

}