package com.example.smd_ass_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    EditText etName, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                Intent intent = new Intent(SignUp.this, PasswordManager.class);
                startActivity(intent);
                finish();
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUp.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    private void addUser() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString();

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
        myDatabaseHelper.open();

        myDatabaseHelper.insert(name, password);

        myDatabaseHelper.close();
    }
}