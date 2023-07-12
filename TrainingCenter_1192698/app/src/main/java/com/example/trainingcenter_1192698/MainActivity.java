package com.example.trainingcenter_1192698;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox rememberMeCheckBox;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.button_login);
        rememberMeCheckBox = findViewById(R.id.checkBox_rememberMe);
        emailEditText = findViewById(R.id.editText_email);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        rememberMeCheckBox.setChecked(rememberMe);

        DatabaseViewer databaseViewer = new DatabaseViewer(this);
        databaseViewer.displayAllTables();

        rememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.apply();
                }
            }
        });

        if (rememberMe) {
            String savedEmail = sharedPreferences.getString("email", "");
            emailEditText.setText(savedEmail);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.editText_email);
                EditText passwordEditText = findViewById(R.id.editText_Password);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putBoolean("rememberMe", rememberMeCheckBox.isChecked());
                editor.apply();

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                String[] loginData = databaseHelper.checkLogin(email, password);

                if (loginData[0] != null) {
                    if (loginData[0].equals("admin")) {
                        Intent adminIntent = new Intent(MainActivity.this, AdminMenu.class);
                        adminIntent.putExtra("adminEmail", loginData[1]);
                        startActivity(adminIntent);
                    } else if (loginData[0].equals("trainee")) {
                        Intent traineeIntent = new Intent(MainActivity.this, TraineeMenu.class);
                        traineeIntent.putExtra("traineeEmail", loginData[1]);
                        startActivity(traineeIntent);
                    }else if (loginData[0].equals("instructor")) {
                        Intent instructorIntent = new Intent(MainActivity.this, InstructorMenu.class);
                        instructorIntent.putExtra("instructorEmail", loginData[1]);
                        startActivity(instructorIntent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signUpButton = findViewById(R.id.button8);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_signup, new SignUpFragment())
                        .commit();
            }
        });
    }
}