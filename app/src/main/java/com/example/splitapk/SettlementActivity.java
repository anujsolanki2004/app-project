package com.example.splitapk;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettlementActivity extends AppCompatActivity {

    ListView settlementListView;
    DatabaseHelper db;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        // Get group ID passed from GroupDetailsActivity
        groupId = getIntent().getIntExtra("group_id", -1);

        // Initialize views
        settlementListView = findViewById(R.id.settlementListView);
        db = new DatabaseHelper(this);

        // Load settlements
        loadSettlements();
    }

    // Load settlements for the group from the database
    private void loadSettlements() {
        Cursor cursor = db.getGroupSettlements();
        if (cursor != null && cursor.getCount() > 0) {
            // Define columns to display in the list view
            String[] fromColumns = new String[]{"member_name", "amount_owed"};
            int[] toViews = new int[]{R.id.settlementNameTextView, R.id.settlementAmountTextView};

            // Create a SimpleCursorAdapter
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.settlement_list_item, // Custom layout for each settlement item
                    cursor,
                    fromColumns,
                    toViews,
                    0
            );

            settlementListView.setAdapter(adapter);  // Set the adapter to the ListView
        } else {
            // If no settlements are found
            Toast.makeText(this, "No settlements found for this group.", Toast.LENGTH_SHORT).show();
        }
    }
}
