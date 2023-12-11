package com.example.assignment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "paperStore.db";
    public static final String TABLE_USER_LOGINS = "user_logins";
    public static final String COL_1  ="ID";
    public static final String COL_3  = "PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //id is NIC
        db.execSQL("create table "+TABLE_USER_LOGINS +"(ID TEXT PRIMARY KEY, PASSWORD TEXT NOT NULL)");
        // Insert initial data
        ContentValues values = new ContentValues();
        values.put("ID", "977420539v");
        values.put("PASSWORD","1234");
        db.insert(TABLE_USER_LOGINS, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_USER_LOGINS);
        onCreate(db);
    }
    public Cursor getUserData(String userId){
        //create instanse of the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_USER_LOGINS+ " WHERE ID = ?",new String[]{String.valueOf(userId)});
        return res;
    }
}
