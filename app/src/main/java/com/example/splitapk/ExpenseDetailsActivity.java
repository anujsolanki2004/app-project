package com.example.splitapk;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailsActivity extends AppCompatActivity {

    TextView expenseNameTextView, amountTextView, participantsTextView;
    DatabaseHelper db;
    int expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

        // Get expense ID passed from GroupDetailsActivity
        expenseId = getIntent().getIntExtra("expense_id", -1);

        // Initialize views
        expenseNameTextView = findViewById(R.id.expenseNameTextView);
        amountTextView = findViewById(R.id.amountTextView);
        participantsTextView = findViewById(R.id.participantsTextView);
        db = new DatabaseHelper(this);

        // Load expense details
        loadExpenseDetails();
    }

    // Load expense details from the database
    @SuppressLint("SetTextI18n")
    private void loadExpenseDetails() {
        Cursor cursor = db.getExpenseDetails();  // Method to get expense details from DB
        if (cursor != null && cursor.moveToFirst()) {
            String expenseName = cursor.getString(cursor.getColumnIndexOrThrow("expense_name"));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
            String participants = cursor.getString(cursor.getColumnIndexOrThrow("participants"));

            expenseNameTextView.setText(expenseName);
            amountTextView.setText("Amount: $" + amount);
            participantsTextView.setText("Participants: " + participants);
        } else {
            Toast.makeText(this, "Expense not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
