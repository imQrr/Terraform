package com.project.terraform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.terraform.data.SeedData;

import java.util.ArrayList;

public class SeedDBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "SeedDataDB";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "seedstorage";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String AMOUNT_COL = "amount";

    private static final String EXPIRATION_COL = "expiration";


    // creating a constructor for our database handler.
    public SeedDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + AMOUNT_COL + " BLOB,"
                + EXPIRATION_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new seeds to our sqlite database.
    public void addNewSeed(String seedName, double seedAmount, String seedExpiration) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, seedName);
        values.put(AMOUNT_COL, seedAmount);
        values.put(EXPIRATION_COL, seedExpiration);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // we have created a new method for reading all the seeds.
    public ArrayList<SeedData> readSeeds()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorSeeds
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<SeedData> seedArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorSeeds.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                seedArrayList.add(new SeedData(
                        cursorSeeds.getString(1),
                        cursorSeeds.getDouble(2),
                        cursorSeeds.getString(3)));
            } while (cursorSeeds.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorSeeds.close();
        return seedArrayList;
    }

    // below is the method for deleting our seed.
    public void deleteSeed(String seedName, String expiration) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // seed and we are comparing it with our seed name.
        db.delete(TABLE_NAME, "name=? AND expiration=?", new String[]{seedName, expiration});
        db.close();
    }

}
