package com.example.trainingcenter_1192698;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class TraineeSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_sign_up);

        emailEditText = findViewById(R.id.editText_email_trainee);
        firstNameEditText = findViewById(R.id.editText_firstName_trainee);
        lastNameEditText = findViewById(R.id.editText_lastName_trainee);
        passwordEditText = findViewById(R.id.editText_password_trainee);
        confirmPasswordEditText = findViewById(R.id.editText_passwordConfirm_trainee);
        phoneEditText = findViewById(R.id.editText_number_trainee);
        addressEditText = findViewById(R.id.editText_address_trainee);
        signUpButton = findViewById(R.id.button_create_trainee_account);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email address");
            emailEditText.requestFocus();
            return;
        }

        if (!isValidName(firstName)) {
            firstNameEditText.setError("Invalid first name");
            firstNameEditText.requestFocus();
            return;
        }

        if (!isValidName(lastName)) {
            lastNameEditText.setError("Invalid last name");
            lastNameEditText.requestFocus();
            return;
        }

        if (!isValidPassword(password)) {
            passwordEditText.setError("Invalid password");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertTraineeUser(email, firstName, lastName, password, phone, address);
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TraineeMenu.class);
        intent.putExtra("traineeEmail", email); // Pass the email as an extra
        startActivity(intent);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidName(String name) {
        int minLength = 3;
        int maxLength = 20;
        return name.length() >= minLength && name.length() <= maxLength;
    }

    private boolean isValidPassword(String password) {
        // Password pattern: At least 8 characters, contains at least one number, one lowercase letter, and one uppercase letter
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
