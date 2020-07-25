package com.example.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "user_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(User.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);

        onCreate(db);

    }

    public long insertUser(String name, String height, String weight, String age, String bmi )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.COLUMN_NAME, name);
        values.put(User.COLUMN_HEIGHT, height);
        values.put(User.COLUMN_WEIGHT, weight);
        values.put(User.COLUMN_AGE, age);
        values.put(User.COLUMN_BMI, bmi);

        long id = db.insert(User.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public User getUser(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.COLUMN_ID, User.COLUMN_NAME,User.COLUMN_HEIGHT, User.COLUMN_WEIGHT, User.COLUMN_AGE, User.COLUMN_BMI},
                User.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_HEIGHT)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_BMI)));

        // close the db connection
        cursor.close();

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + User.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)));
                user.setWeight(cursor.getString(cursor.getColumnIndex(User.COLUMN_WEIGHT)));
                user.setHeight(cursor.getString(cursor.getColumnIndex(User.COLUMN_HEIGHT)));
                user.setAge(cursor.getString(cursor.getColumnIndex(User.COLUMN_AGE)));
                user.setBmi(cursor.getString(cursor.getColumnIndex(User.COLUMN_BMI)));

                users.add(user);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return users;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + User.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_WEIGHT, user.getWeight());
        values.put(User.COLUMN_HEIGHT, user.getHeight());
        values.put(User.COLUMN_AGE, user.getAge());
        values.put(User.COLUMN_BMI, user.getBmi());

        // updating row
        return db.update(User.TABLE_NAME, values, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
