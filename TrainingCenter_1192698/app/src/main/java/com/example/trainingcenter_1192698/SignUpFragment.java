package com.example.trainingcenter_1192698;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class SignUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpInstructorButton = rootView.findViewById(R.id.button_signup_instructor);
        Button signUpAdminButton = rootView.findViewById(R.id.button_signup_admin);
        Button signUpTraineeButton = rootView.findViewById(R.id.button_signup_trainee);

        signUpInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstructorSignUpActivity.class);
                startActivity(intent);
            }
        });

        signUpAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminSignUpActivity.class);
                startActivity(intent);
            }
        });

        signUpTraineeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TraineeSignUpActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}
