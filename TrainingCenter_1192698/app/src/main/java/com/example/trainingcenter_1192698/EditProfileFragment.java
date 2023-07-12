package com.example.trainingcenter_1192698;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    private EditText FirstName;
    private EditText LastName;
    private EditText Phone;
    private EditText Address;
    private Button saveButton;

    EditProfileFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Bundle arguments = getArguments();
        String traineeEmail = arguments.getString("traineeEmail");

        FirstName = view.findViewById(R.id.editText_firstName_trainee_edit);
        LastName = view.findViewById(R.id.editText_lastName_trainee_edit);
        Phone = view.findViewById(R.id.editText_number_trainee_edit);
        Address = view.findViewById(R.id.editText_address_trainee_edit);
        saveButton = view.findViewById(R.id.button_save_trainee_profile);

        DatabaseHelper databaseHelper=new DatabaseHelper(getContext());
        ArrayList<String> data=databaseHelper.getTraineeInformation(traineeEmail);

        FirstName.setText(data.get(0));
        LastName.setText(data.get(1));
        Phone.setText(data.get(2));
        Address.setText(data.get(3));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName=FirstName.getText().toString(),
                        newLastName=LastName.getText().toString(),
                        newPhone=Phone.getText().toString(),
                        newAddress=Address.getText().toString();

                databaseHelper.updateTraineeData(traineeEmail,newFirstName,newLastName,newPhone,newAddress);
                Toast.makeText(getContext(), "Profile information updated!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}