package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Activity;

public class MainActivity extends Activity {

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;

    RadioGroup rgClass;

    Spinner spMajor;

    CheckBox cbNotifications;
    CheckBox cbTerms;

    Button btnLogin;

    TextView tvResult;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);

        rgClass = findViewById(R.id.rgClass);

        spMajor = findViewById(R.id.spMajor);

        cbNotifications = findViewById(R.id.cbNotifications);
        cbTerms = findViewById(R.id.cbTerms);

        btnLogin = findViewById(R.id.btnLogin);

        tvResult = findViewById(R.id.tvResult);

        String[] majors = {
                "בחר מגמה",
                "מחשבים",
                "פיזיקה",
                "ביולוגיה",
                "כימיה"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                majors
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spMajor.setAdapter(adapter);

        preferences = getSharedPreferences(
                "StudentData",
                MODE_PRIVATE
        );

        loadStudent();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {

        String firstName =
                etFirstName.getText().toString().trim();

        String lastName =
                etLastName.getText().toString().trim();

        String email =
                etEmail.getText().toString().trim();

        boolean valid = true;

        if (firstName.isEmpty()) {
            etFirstName.setError(
                    getString(R.string.error_first_name)
            );
            valid = false;
        }

        if (lastName.isEmpty()) {
            etLastName.setError(
                    getString(R.string.error_last_name)
            );
            valid = false;
        }

        if (email.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.setError(
                    getString(R.string.error_email)
            );
            valid = false;
        }

        int selectedClassId =
                rgClass.getCheckedRadioButtonId();

        if (selectedClassId == -1) {
            valid = false;
        }

        if (spMajor.getSelectedItemPosition() == 0) {
            valid = false;
        }

        if (!cbTerms.isChecked()) {
            cbTerms.setError(
                    getString(R.string.error_terms)
            );
            valid = false;
        }

        if (!valid) {
            return;
        }

        RadioButton selectedRadioButton =
                findViewById(selectedClassId);

        String studentClass =
                selectedRadioButton.getText().toString();

        String major =
                spMajor.getSelectedItem().toString();

        boolean notifications =
                cbNotifications.isChecked();

        boolean terms =
                cbTerms.isChecked();

        Student student = new Student(
                firstName,
                lastName,
                email,
                studentClass,
                major,
                notifications,
                terms
        );

        saveStudent(student);

        showStudent(student);
    }

    private void saveStudent(Student student) {

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putString(
                "firstName",
                student.getFirstName()
        );

        editor.putString(
                "lastName",
                student.getLastName()
        );

        editor.putString(
                "email",
                student.getEmail()
        );

        editor.putString(
                "studentClass",
                student.getStudentClass()
        );

        editor.putString(
                "major",
                student.getMajor()
        );

        editor.putBoolean(
                "notifications",
                student.isNotifications()
        );

        editor.putBoolean(
                "terms",
                student.isTerms()
        );

        editor.putBoolean(
                "hasStudent",
                true
        );

        editor.apply();
    }

    private void loadStudent() {

        boolean hasStudent =
                preferences.getBoolean(
                        "hasStudent",
                        false
                );

        if (!hasStudent) {
            return;
        }

        String firstName =
                preferences.getString(
                        "firstName",
                        ""
                );

        String lastName =
                preferences.getString(
                        "lastName",
                        ""
                );

        String email =
                preferences.getString(
                        "email",
                        ""
                );

        String studentClass =
                preferences.getString(
                        "studentClass",
                        ""
                );

        String major =
                preferences.getString(
                        "major",
                        ""
                );

        boolean notifications =
                preferences.getBoolean(
                        "notifications",
                        false
                );

        boolean terms =
                preferences.getBoolean(
                        "terms",
                        false
                );

        Student student = new Student(
                firstName,
                lastName,
                email,
                studentClass,
                major,
                notifications,
                terms
        );

        showStudent(student);
    }

    private void showStudent(Student student) {

        String result =
                "שם פרטי: " +
                        student.getFirstName() +
                        "\nשם משפחה: " +
                        student.getLastName() +
                        "\nאימייל: " +
                        student.getEmail() +
                        "\nכיתה: " +
                        student.getStudentClass() +
                        "\nמגמה: " +
                        student.getMajor() +
                        "\nקבלת התראות: " +
                        student.isNotifications();

        tvResult.setText(result);

        tvResult.setVisibility(View.VISIBLE);
    }
}