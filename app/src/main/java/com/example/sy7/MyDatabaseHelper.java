package com.example.sy7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

public  static final String CREATE_CONTACTS="create table Contacts("
        +"id integer primary key autoincrement,"
        +"name text,"
        +"number text,"
        +"sex text)";

    public  static final String CREATE_CATEGORY="create table Category("
            +"id integer primary key autoincrement,"
            +"category_name text,"
            +"category_code integer)";

    private  Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS);
        db.execSQL(CREATE_CATEGORY);
        //Toast.makeText(mContext,"εε»Ίζε",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Contacts");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}