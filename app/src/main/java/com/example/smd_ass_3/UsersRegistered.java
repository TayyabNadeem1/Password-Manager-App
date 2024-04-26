package com.example.smd_ass_3;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    FloatingActionButton fabDelete;
    ArrayList<User> user;

    ImageView ivEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_registered);
        init();

    }
    private void init()
    {

        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(manager);
        fabDelete =findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsersRegistered.this, DeletedEntries.class));
            }
        });


        DatabaseHelper database = new DatabaseHelper(this);
        database.open();
        user = database.readAllContacts();
        database.close();

        adapter = new UserAdapter(this, user);
        rvUsers.setAdapter(adapter);
    }

}