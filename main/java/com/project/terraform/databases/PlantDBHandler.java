// Import necessary libraries and classes
package com.project.terraform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.project.terraform.data.Crop;
import java.util.ArrayList;

// PlantDBHandler class that extends SQLiteOpenHelper
public class PlantDBHandler extends SQLiteOpenHelper {

    // Database constants including database name, version, table name, and column names.
    private static final String DB_NAME = "PlantDataDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "plantedcrops";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String AREA_COL = "area";
    private static final String PLANT_DATE_COL = "plant_date";
    private static final String IMAGE_THUMBNAIL_COL = "plant_thumbnail";

    // Constructor that only takes context as a parameter.
    public PlantDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Method called when the database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table using SQLite Create Table Statement.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + AREA_COL + " TEXT, "
                + PLANT_DATE_COL + " TEXT, "
                + IMAGE_THUMBNAIL_COL + " BLOB)";
        db.execSQL(query);
    }

    // Method to add a new plant to the database taking plantName, plantArea, plantingDate, and picture in byte form as parameters.
    public void addNewPlant(String plantName, String plantArea, String plantingDate, byte[] thumbnail) {
        SQLiteDatabase db = this.getWritableDatabase(); //Object db to allow writing into the database.
        ContentValues values = new ContentValues();
        values.put(NAME_COL, plantName);
        values.put(AREA_COL, plantArea);
        values.put(PLANT_DATE_COL, plantingDate);
        values.put(IMAGE_THUMBNAIL_COL, thumbnail);
        db.insert(TABLE_NAME, null, values); // Add said data into the database.
        db.close();
    }

    // Method called when the database is upgraded (e.g., change in version).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to retrieve all plants from the database includes plantName, plantArea, plantingDate, and picture in byte form.
    public ArrayList<Crop> readPlants() {
        SQLiteDatabase db = this.getReadableDatabase(); //Object db to allow reading from the database.
        Cursor cursorPlants = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Crop> plantArrayList = new ArrayList<>();

        //If statement to begin the while loop within the statement.
        if (cursorPlants.moveToFirst()) {
            do {
                // Retrieve plant data and create Crop object.
                byte[] bitmap_array = cursorPlants.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(bitmap_array, 0, bitmap_array.length); //Convert the byte form of the image into a Bitmap version.
                Crop newCrop = new Crop(
                        cursorPlants.getString(1),
                        cursorPlants.getString(2),
                        cursorPlants.getString(3),
                        bitmap_array, image);
                plantArrayList.add(newCrop); //Add Crop object to the arrayList.
            } while (cursorPlants.moveToNext());
        }

        cursorPlants.close();
        return plantArrayList;
    }

    // Method to delete a plant from the database.
    public void deletePlant(String plantArea) {
        SQLiteDatabase db = this.getWritableDatabase(); //Object db to write into the database.
        db.delete(TABLE_NAME, "area=?", new String[]{plantArea});
        db.close();
    }

    // Method to check if an area exists in the database.
    public boolean isAreaInDatabase(String areaToCheck) {
        SQLiteDatabase db = this.getReadableDatabase(); //Object db to read from the database.
        String columnName = AREA_COL;

        // Query to check if the area exists in the database (case-insensitive).
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME +
                " WHERE LOWER(" + columnName + ") = LOWER(?)";

        // Arguments for the query.
        String[] selectionArgs = {areaToCheck};

        try {
            Cursor cursor = db.rawQuery(query, selectionArgs);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            db.close();

            // If count > 0, area exists within the database.
            return count > 0; // Returns true.

            // Catches any exceptions that might occur during the execution of the database operations
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false because catch was processed.
        }
    }
}
