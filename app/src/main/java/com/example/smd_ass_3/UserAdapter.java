package com.example.smd_ass_3;


import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> users;

    public static ArrayList<User> deletedUsers; // List to store deleted users
    private Context context;

    ImageView ivDelete, ivEdit;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.deletedUsers = new ArrayList<>(); // Initialize the list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_single_user, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getPassword());

        ivDelete = holder.itemView.findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Store the deleted user before removing it from the list
                        User deletedUser = users.get(holder.getAdapterPosition());
                        deletedUsers.add(deletedUser);

                        // Delete the user from the database and the list
                        DatabaseHelper database = new DatabaseHelper(context);
                        database.open();
                        database.deleteContact(deletedUser.getId());
                        database.close();

                        users.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the deletion
                    }
                });

                deleteDialog.show();
            }
        });


    }

    // Method to get the list of deleted users
    public ArrayList<User> getDeletedUsers() {
        return deletedUsers;
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvPassword);
        }
    }
}
