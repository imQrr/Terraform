package com.project.terraform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.terraform.data.FarmerInfo;

// UserDBHandler class that extends SQLiteOpenHelper
public class UserDBHandler extends SQLiteOpenHelper {

    // Database constants including database name, version, table name, and column names.
    private static final String DB_NAME = "UserInfoDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user_info";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";

    // Constructor that only takes context as a parameter.
    public UserDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Method called when the database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table using SQLite Create Table Statement.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + EMAIL_COL + " TEXT, "
                + PASSWORD_COL + " TEXT)";
        db.execSQL(query); // Execute the SQL query.
    }

    // Method to add a new user to the database taking FarmerInfo object as a parameter.
    public void addUser(FarmerInfo farmer) {
        SQLiteDatabase db = this.getWritableDatabase(); // Object db to allow writing into the database.
        ContentValues values = new ContentValues(); // Create ContentValues to add data into the database.
        values.put(NAME_COL, farmer.getName());
        values.put(EMAIL_COL, farmer.getEmail());
        values.put(PASSWORD_COL, farmer.getPassword());
        db.insert(TABLE_NAME, null, values); // Insert data into the database.
        db.close();  // Close the database connection.
    }

    // Method called when the database is upgraded (e.g., change in version).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate the table if it already exists.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Recreate the updated database.
    }

    // Method to check if a user already exists in the database based on email.
    public boolean checkUser(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase(); // Object db to read from the database.
        String[] columns = {ID_COL};
        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=?", new String[]{farmer.getEmail()}, null, null, null);
        // Query to check if the user exists in the database.
        boolean userExists = cursorUsers.moveToFirst();
        cursorUsers.close();
        return userExists; // Returns true if exists, else false.
    }

    // Method to check if login credentials are valid (email and password combination).
    public boolean checkLogin(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase(); // Object db to read from the database.
        String[] columns = {ID_COL};
        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=? AND password=?", new String[]{farmer.getEmail(), farmer.getPassword()}, null, null, null);
        // Query to check if the login credentials exists in the database.
        boolean loginSuccessful = cursorUsers.moveToFirst();
        cursorUsers.close();
        return loginSuccessful; //Return true if they exist in the database, else false.
    }

    // Method to retrieve the name of the user based on email and password.
    public String getName(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase(); // Object db to read from the database.
        String[] columns = {NAME_COL};
        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=? AND password=?", new String[]{farmer.getEmail(), farmer.getPassword()}, null, null, null);
        // Query to find the tuple that contains the corresponding email and password.
        String farmerName = null;

        if (cursorUsers.moveToFirst()) {
            // Check if the column index is valid.
            int nameColumnIndex = cursorUsers.getColumnIndex(NAME_COL);
            if (nameColumnIndex != -1) {
                farmerName = cursorUsers.getString(nameColumnIndex);
            }
        }
        cursorUsers.close();
        return farmerName; // Return String containing name of the corresponding email and password.
    }
}
