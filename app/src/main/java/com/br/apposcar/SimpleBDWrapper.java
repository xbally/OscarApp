package com.br.apposcar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SimpleBDWrapper extends SQLiteOpenHelper {


public  static final String USER = "Usuario";
public  static final String USER_ID = "_id";
public  static final String USER_EMAIL = "_email";
    public  static final String USER_SENHA = "_senha";


private static  final String DATABASE_NAME = "Students.db";

private static final int DATABASE_VERSION =1;

private static final String DATABASE_CREATE = "create table " + USER
        + "(" + USER_ID + " integer primary key autoincrement, "
        + USER_EMAIL +  "text not null,"  + USER_SENHA + "text not null);";

public SimpleBDWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

@Override
public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        }

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        onCreate(db);
        }


}