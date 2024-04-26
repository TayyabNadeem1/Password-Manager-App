package com.example.smd_ass_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PasswordManager extends AppCompatActivity {

    Button btnSignUp, btnLogin, btnPM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_manager);
        btnPM = findViewById(R.id.btnPM);
        btnPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordManager.this, UsersRegistered.class);
                startActivity(intent);
                finish();
            }
        });
//        btnLogin = findViewById(R.id.btnLogin);
//

    }
}