package com.example.splitapk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BillSplit.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_GROUPS = "Groups";
    private static final String TABLE_EXPENSES = "Expenses";
    private static final String TABLE_TRANSACTIONS = "Transactions";

    // Users table columns
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "name";

    // Groups table columns
    private static final String GROUP_ID = "group_id";
    private static final String GROUP_NAME = "group_name";

    // Expenses table columns
    private static final String EXPENSE_ID = "expense_id";
    private static final String EXPENSE_NAME = "expense_name";
    private static final String EXPENSE_AMOUNT = "amount";
    private static final String EXPENSE_DATE = "date";

    // Transactions table columns
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String PAID_BY = "paid_by"; // user_id of the payer
    private static final String PAID_TO = "paid_to"; // user_id of the payee
    private static final String TRANSACTION_AMOUNT = "amount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating tables
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT)";

        String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + " (" +
                GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GROUP_NAME + " TEXT)";

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXPENSE_NAME + " TEXT, " +
                EXPENSE_AMOUNT + " REAL, " +
                EXPENSE_DATE + " TEXT)";

        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PAID_BY + " INTEGER, " +
                PAID_TO + " INTEGER, " +
                TRANSACTION_AMOUNT + " REAL)";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic, like dropping tables if exists and recreating them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public void addGroup() {
    }

    public void addExpense(double ignoredV) {
    }

    public Cursor getAllGroups() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT group_id, group_name FROM Groups";
        return db.rawQuery(query, null);
    }

    public Cursor getGroupExpenses(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT expense_id, expense_name, amount, date FROM Expenses WHERE group_id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(groupId)});
    }

    public Cursor getGroupDetails() {
        return null;
    }

    public Cursor getExpenseDetails() {
        return null;
    }

    public Cursor getGroupSettlements() {
        return null;
    }


    // Add methods to insert, query, update, and delete data
}

