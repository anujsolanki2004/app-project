package com.example.splitapk;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreateGroup;
    ListView groupListView;
    DatabaseHelper db;
    SimpleCursorAdapter groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateGroup = findViewById(R.id.btnCreateGroup);
        groupListView = findViewById(R.id.groupListView);
        db = new DatabaseHelper(this);

        // Load group data into the ListView
        loadGroups();

        // Handle create new group button click
        btnCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GroupActivity.class);
            startActivity(intent);
        });

        // Handle group list item click to view group details
        groupListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected group's ID from the Cursor
            Cursor cursor = (Cursor) groupListView.getItemAtPosition(position);
            int groupId = cursor.getInt(cursor.getColumnIndexOrThrow("group_id"));

            // Start GroupDetailsActivity to show details for the selected group
            Intent intent = new Intent(MainActivity.this, GroupDetailsActivity.class);
            intent.putExtra("group_id", groupId);
            startActivity(intent);
        });
    }

    // Load all groups from the database and set them in the ListView
    private void loadGroups() {
        Cursor cursor = db.getAllGroups(); // Method to get all groups from DB
        if (cursor != null) {
            // Columns to display in the ListView
            String[] from = new String[]{"group_name"};
            int[] to = new int[]{android.R.id.text1};

            // Set up the adapter to map the database columns to the ListView
            groupAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    from,
                    to,
                    0
            );
            groupListView.setAdapter(groupAdapter);
        } else {
            Toast.makeText(this, "No groups found. Create a new group!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload groups when returning to the activity
        loadGroups();
    }
}
