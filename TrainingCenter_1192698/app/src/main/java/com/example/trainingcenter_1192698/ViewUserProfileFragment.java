package com.example.trainingcenter_1192698;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewUserProfileFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewUserProfileFragment extends Fragment {
    private Spinner profileSpinner;
    private Button submitButton;
    ViewUserProfileFragment(){};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_user_profile, container, false);

        profileSpinner = view.findViewById(R.id.spinner_profile_id);
        submitButton = view.findViewById(R.id.button_view_profile);
        populateUsersSpinner();

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUser = profileSpinner.getSelectedItem().toString();
                String userId = selectedUser.split(":")[1];
                System.out.println(userId);
                TextView viewInfo = getView().findViewById(R.id.text_view_profile);
                viewInfo.setText(databaseHelper.getUserInformation(userId));
            }
        });

        return view;
    }

    private void populateUsersSpinner() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        ArrayList<String> usersList = databaseHelper.getAllUsersInfo();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, usersList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        profileSpinner.setAdapter(adapter);
    }

}