package com.example.splitapk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
ANUJBHAI!!!!!!!!!!!!!!

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivty extends AppCompatActivity {

    EditText expenseNameEditText, amountEditText;
    Button addExpenseButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEditText = findViewById(R.id.expenseNameEditText);
        amountEditText = findViewById(R.id.amountEditText);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        db = new DatabaseHelper(this);

        addExpenseButton.setOnClickListener(v -> {
            String expenseName = expenseNameEditText.getText().toString();
            String amount = amountEditText.getText().toString();

            if (!expenseName.isEmpty() && !amount.isEmpty()) {
                db.addExpense(Double.parseDouble(amount));  // Add this method
                Toast.makeText(AddExpenseActivty.this, "Expense Added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddExpenseActivty.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

