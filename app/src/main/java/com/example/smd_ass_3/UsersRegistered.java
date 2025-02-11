package com.example.smd_ass_3;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UsersRegistered extends AppCompatActivity {

    RecyclerView rvUsers;
    LinearLayoutManager manager;
    UserAdapter adapter;
    FloatingActionButton fabDelete, fabAdd;
    ArrayList<User> user;

    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_registered);
        init();
        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);

    }
    private void init()
    {

        rvUsers = findViewById(R.id.rvUsers);

        fabDelete =findViewById(R.id.fabDelete);
        fabAdd =findViewById(R.id.fabAdd);

        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsersRegistered.this, DeletedEntries.class));
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersRegistered.this, AddNamePass.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
                finish();
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();
        user = databaseHelper.readAllUrls(userid);
        databaseHelper.close();

        adapter = new UserAdapter(this, user);
        rvUsers.setAdapter(adapter);

    }

}