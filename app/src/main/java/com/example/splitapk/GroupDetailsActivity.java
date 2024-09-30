package com.example.splitapk;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroupDetailsActivity extends AppCompatActivity {

    TextView groupNameTextView;
    Button btnAddExpense, btnViewSettlements;
    ListView expensesListView;
    DatabaseHelper db;
    SimpleCursorAdapter expensesAdapter;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        // Get group ID passed from MainActivity
        groupId = getIntent().getIntExtra("group_id", -1);

        // Initialize views
        groupNameTextView = findViewById(R.id.groupNameTextView);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewSettlements = findViewById(R.id.btnViewSettlements);
        expensesListView = findViewById(R.id.expensesListView);
        db = new DatabaseHelper(this);

        // Load group details
        loadGroupDetails();

        // Load expenses for the group
        loadExpenses();

        // Handle "Add Expense" button click
        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(GroupDetailsActivity.this, AddExpenseActivty.class);
            intent.putExtra("group_id", groupId);  // Pass the group ID to AddExpenseActivity
            startActivity(intent);
        });

        // Handle "View Settlements" button click
        btnViewSettlements.setOnClickListener(v -> {
            Intent intent = new Intent(GroupDetailsActivity.this, SettlementActivity.class);
            intent.putExtra("group_id", groupId);  // Pass group ID to view settlements
            startActivity(intent);
        });

        // Handle expense list item click to view detailed expense (optional feature)
        expensesListView.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) expensesListView.getItemAtPosition(position);
            int expenseId = cursor.getInt(cursor.getColumnIndexOrThrow("expense_id"));

            // Start ExpenseDetailsActivity (optional feature) to view detailed information about the expense
            Intent intent = new Intent(GroupDetailsActivity.this, ExpenseDetailsActivity.class);
            intent.putExtra("expense_id", expenseId);
            startActivity(intent);
        });
    }

    // Load group name and display it
    private void loadGroupDetails() {
        Cursor cursor = db.getGroupDetails();  // Method to get group details from DB
        if (cursor != null && cursor.moveToFirst()) {
            String groupName = cursor.getString(cursor.getColumnIndexOrThrow("group_name"));
            groupNameTextView.setText(groupName);
        } else {
            Toast.makeText(this, "Group not found!", Toast.LENGTH_SHORT).show();
        }
    }

    // Load all expenses for the selected group
    private void loadExpenses() {
        Cursor cursor = db.getGroupExpenses(groupId);  // Method to get group expenses from DB
        if (cursor != null) {
            String[] from = new String[]{"expense_name", "amount", "date"};
            int[] to = new int[]{R.id.expenseNameTextView, R.id.expenseAmountTextView, R.id.expenseDateTextView};

            expensesAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.expense_list_item,  // Custom layout for each expense item
                    cursor,
                    from,
                    to,
                    0
            );
            expensesListView.setAdapter(expensesAdapter);
        } else {
            Toast.makeText(this, "No expenses found for this group.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload expenses when returning to the activity
        loadExpenses();
    }
}
