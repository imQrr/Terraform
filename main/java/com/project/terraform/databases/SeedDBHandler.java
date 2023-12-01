// Import necessary libraries and classes
package com.project.terraform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.terraform.data.SeedData;

import java.util.ArrayList;

// SeedDBHandler class that extends SQLiteOpenHelper
public class SeedDBHandler extends SQLiteOpenHelper {

    // Database constants including database name, version, table name, and column names.
    private static final String DB_NAME = "SeedDataDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "seedstorage";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String AMOUNT_COL = "amount";
    private static final String EXPIRATION_COL = "expiration";

    // Constructor that only takes context as a parameter.
    public SeedDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Method called when the database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the database table.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + AMOUNT_COL + " BLOB,"
                + EXPIRATION_COL + " TEXT)";
        db.execSQL(query); // Execute the SQL query.
    }

    // Method to add new seed data to the database taking seedName, seedAmount, seedExpiration as parameters.
    public void addNewSeed(String seedName, double seedAmount, String seedExpiration) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database.
        ContentValues values = new ContentValues(); // Create ContentValues to store data.
        values.put(NAME_COL, seedName);
        values.put(AMOUNT_COL, seedAmount);
        values.put(EXPIRATION_COL, seedExpiration);
        db.insert(TABLE_NAME, null, values); // Insert data into the database.
        db.close(); // Close the database connection
    }

    // Method called when the database is upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate the table if it already exists.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to read all seeds from the database
    public ArrayList<SeedData> readSeeds() {
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database.
        Cursor cursorSeeds = db.rawQuery("SELECT * FROM " + TABLE_NAME, null); // Query to read data.
        ArrayList<SeedData> seedArrayList = new ArrayList<>(); // Create an ArrayList to store seed data.

        if (cursorSeeds.moveToFirst()) {
            // Loop through the cursor to fetch seed data and add it to the ArrayList.
            do {
                seedArrayList.add(new SeedData(
                        cursorSeeds.getString(1),
                        cursorSeeds.getDouble(2),
                        cursorSeeds.getString(3)));
            } while (cursorSeeds.moveToNext());
        }
        cursorSeeds.close(); // Close the cursor.
        return seedArrayList; // Return the list of seeds.
    }

    // Method to delete a seed from the database.
    public void deleteSeed(String seedName, String expiration) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database.
        // Delete the seed based on name and expiration using SQLite query.
        db.delete(TABLE_NAME, "name=? AND expiration=?", new String[]{seedName, expiration});
        db.close(); // Close the database connection
    }
}
