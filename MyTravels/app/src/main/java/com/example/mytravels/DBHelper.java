package com.example.mytravels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Database to store username and password
public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "MyDatabase.db";

    public DBHelper(@Nullable Context context) {
        super(context, "MyDatabase.db", null, 1);
    }

    // Creating tables for user and for reservation
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username primary key, password TEXT)");
        myDB.execSQL("create Table reservation(id INTEGER primary key AUTOINCREMENT, username TEXT, hotelName TEXT, hotelPrice REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
        myDB.execSQL("drop Table if exists reservation");
    }

    // Insert in username
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    // Insert in reservartion
    public Boolean insertData(String username, String hotelName, double hotelPrice) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("hotelName", hotelName);
        contentValues.put("hotelPrice", hotelPrice);
        long result = myDB.insert("reservation", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    // Get data of reservations of a specific user
    public Cursor read_all_data(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from reservation where username = ?", new String[]{user});
    }

    // Update the password
    public boolean updatePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = db.update("users", contentValues,  "username = ? " , new String[] {username});
        if(result == -1) return false;
        else
            return true;
    }

    // Check if the username exists
    public  Boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0)
            return  true;
        else
            return false;
    }

    // Check if password and username exist
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

}
