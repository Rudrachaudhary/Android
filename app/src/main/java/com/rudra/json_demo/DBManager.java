package com.rudra.json_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Rudra on 11/29/2017.
 */

public class DBManager {

    private SQLiteDatabase sqlDB;
    static  final String DBName="Resources";
    static  final String TableName="Posts";
    static  final String ColSN="SN";
    static  final String ColUserId="UserId";
    static  final String ColId="Id";
    static  final String ColTitle="Title";
    static  final String ColBody="Body";
    static final int DBVersion=1;

    static final String CreateTable="Create table IF NOT EXISTS" +TableName+ "("+ ColSN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ColUserId + " integer," + ColId + " integer," + ColTitle + " text,"
            + ColBody + " text);";

    private static class DatabaseHelperUser extends SQLiteOpenHelper{

        private final Context context;

        public DatabaseHelperUser(Context context) {
            super(context, DBName, null, DBVersion);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CreateTable);
            Toast.makeText(context,"Table is created",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("Drop table IF EXISTS "+ TableName);
            onCreate(db);

        }
    }

    public DBManager(Context context){

        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB=db.getWritableDatabase();

    }

    public  long Insert(ContentValues values){
        long ID=sqlDB.insert(TableName,"",values);
        return ID;
    }
}
