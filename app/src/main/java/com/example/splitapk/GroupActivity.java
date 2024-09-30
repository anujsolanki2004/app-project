package com.example.splitapk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroupActivity extends AppCompatActivity {

    EditText groupNameEditText;
    Button addGroupButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        addGroupButton = findViewById(R.id.btnCreateGroup);
        db = new DatabaseHelper(this);

        addGroupButton.setOnClickListener(v -> {
            String groupName = groupNameEditText.getText().toString();
            if (!groupName.isEmpty()) {
                db.addGroup();  // Create a method to add groups
                Toast.makeText(GroupActivity.this, "Group Created", Toast.LENGTH_SHORT).show();
                finish();  // Go back to the main activity
            } else {
                Toast.makeText(GroupActivity.this, "Enter a group name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
