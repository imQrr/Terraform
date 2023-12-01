package com.project.terraform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.terraform.data.FarmerInfo;

public class UserDBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "UserInfoDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user_info";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";

    public UserDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + EMAIL_COL + " TEXT, "
                + PASSWORD_COL + " TEXT)";
        db.execSQL(query);
    }

    public void addUser(FarmerInfo farmer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, farmer.getName());
        values.put(EMAIL_COL, farmer.getEmail());
        values.put(PASSWORD_COL, farmer.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean checkUser(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {ID_COL};

        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=?", new String[]{farmer.getEmail()}, null, null, null);

        boolean userExists = cursorUsers.moveToFirst();

        cursorUsers.close();
        return userExists;
    }

    public boolean checkLogin(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {ID_COL};

        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=? AND password=?", new String[]{farmer.getEmail(), farmer.getPassword()}, null, null, null);

        boolean loginSuccessful = cursorUsers.moveToFirst();

        cursorUsers.close();
        return loginSuccessful;
    }

    public String getName(FarmerInfo farmer) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {NAME_COL}; // Add the column for the name

        Cursor cursorUsers = db.query(TABLE_NAME, columns, "email=? AND password=?", new String[]{farmer.getEmail(), farmer.getPassword()}, null, null, null);

        String farmerName = null;

        if (cursorUsers.moveToFirst()) {
            // Check if the column index is valid
            int nameColumnIndex = cursorUsers.getColumnIndex(NAME_COL);
            if (nameColumnIndex != -1) {
                farmerName = cursorUsers.getString(nameColumnIndex);
            }
        }

        cursorUsers.close();
        return farmerName;
    }

}
