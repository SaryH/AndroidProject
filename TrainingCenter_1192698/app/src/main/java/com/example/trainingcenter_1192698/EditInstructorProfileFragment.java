package com.example.trainingcenter_1192698;

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
 * Use the {@link EditInstructorProfileFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInstructorProfileFragment extends Fragment {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText specializationEditText;
    private Spinner degreeSpinner;
    private EditText coursesEditText;
    private Button saveProfileButton;

    EditInstructorProfileFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_instructor_profile, container, false);

        Bundle arguments = getArguments();
        String instructorEmail = arguments.getString("instructorEmail");

        firstNameEditText = view.findViewById(R.id.editText_firstName_Instructor_edit);
        lastNameEditText = view.findViewById(R.id.editText_lastName_Instructor_edit);
        phoneEditText = view.findViewById(R.id.editText_number_Instructor_edit);
        addressEditText = view.findViewById(R.id.editText_address_Instructor_edit);
        specializationEditText = view.findViewById(R.id.editText_speccialization_Instructor_edit);
        degreeSpinner = view.findViewById(R.id.spinner_degree_edit);
        coursesEditText = view.findViewById(R.id.editText_courses_Instructor_edit);
        saveProfileButton = view.findViewById(R.id.button_edit_instructor_account);

        DatabaseHelper databaseHelper=new DatabaseHelper(getContext());
        ArrayList<String> data=databaseHelper.getInstructorData(instructorEmail);

        firstNameEditText.setText(data.get(0));
        lastNameEditText.setText(data.get(1));
        phoneEditText.setText(data.get(2));
        addressEditText.setText(data.get(3));
        specializationEditText.setText(data.get(4));
        List<String> degreeList = new ArrayList<>();
        degreeList.add("BSc");
        degreeList.add("MSc");
        degreeList.add("PhD");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, degreeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeSpinner.setAdapter(adapter);
        coursesEditText.setText(data.get(6));

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName=firstNameEditText.getText().toString(),
                        newLastName=lastNameEditText.getText().toString(),
                        newPhone=phoneEditText.getText().toString(),
                        newAddress=addressEditText.getText().toString(),
                        newSpecialization=specializationEditText.getText().toString(),
                        newDegree=degreeSpinner.getSelectedItem().toString(),
                        newCourses=coursesEditText.getText().toString();

                databaseHelper.updateInstructorInformation(instructorEmail,newFirstName,newLastName,newPhone,newAddress,newSpecialization,newDegree,newCourses);
                Toast.makeText(getContext(), "Profile information updated!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}