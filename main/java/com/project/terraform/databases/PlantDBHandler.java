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

public class PlantDBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "PlantDataDB";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "plantedcrops";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String AREA_COL = "area";

    private static final String PLANT_DATE_COL = "plant_date";

    private static final String IMAGE_THUMBNAIL_COL = "plant_thumbnail";

    public PlantDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + AREA_COL + " TEXT, "
                + PLANT_DATE_COL + " TEXT, "
                + IMAGE_THUMBNAIL_COL + " BLOB)";

        db.execSQL(query);
    }

    public void addNewPlant(String plantName, String plantArea, String plantingDate, byte[] thumbnail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, plantName);
        values.put(AREA_COL, plantArea);
        values.put(PLANT_DATE_COL, plantingDate);
        values.put(IMAGE_THUMBNAIL_COL, thumbnail);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Crop> readPlants()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorPlants
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Crop> plantArrayList
                = new ArrayList<>();

        if (cursorPlants.moveToFirst()) {
            do {
                byte[] bitmap_array = cursorPlants.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(bitmap_array, 0, bitmap_array.length);
                Crop newCrop = new Crop(
                        cursorPlants.getString(1),
                        cursorPlants.getString(2),
                        cursorPlants.getString(3),
                        bitmap_array, image);
                plantArrayList.add(newCrop);
            } while (cursorPlants.moveToNext());
        }

        cursorPlants.close();
        return plantArrayList;
    }

    public void deletePlant(String plantArea) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "area=?", new String[]{plantArea});
        db.close();
    }

    public boolean isAreaInDatabase(String areaToCheck) {
        SQLiteDatabase db = this.getReadableDatabase();

        String columnName = AREA_COL;

        // Query to check if the area exists in the database (case-insensitive)
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME +
                " WHERE LOWER(" + columnName + ") = LOWER(?)";

        // Arguments for the query
        String[] selectionArgs = {areaToCheck};

        try {
            // Execute the query
            Cursor cursor = db.rawQuery(query, selectionArgs);

            // Move to the first row of the result
            cursor.moveToFirst();

            // Get the count from the result
            int count = cursor.getInt(0);

            // Close the cursor and database
            cursor.close();
            db.close();

            // If count is greater than 0, the area exists in the database
            return count > 0;
        } catch (Exception e) {
            // Handle any exceptions here (e.g., database query error)
            e.printStackTrace();
            return false;
        }
    }
}
