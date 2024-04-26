package com.example.smd_ass_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeletedEntries extends AppCompatActivity {


    RecyclerView rvDeletedEntries;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deleted_entries);


        // Initialize RecyclerView and set layout manager
        rvDeletedEntries = findViewById(R.id.rvDeletedEntries);
        rvDeletedEntries.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve deleted users from the UserAdapter
        ArrayList<User> deletedUsers = UserAdapter.deletedUsers;

        // Set up adapter for RecyclerView with the retrieved deleted users
        adapter = new UserAdapter(this, deletedUsers);
        rvDeletedEntries.setAdapter(adapter);
    }
}
