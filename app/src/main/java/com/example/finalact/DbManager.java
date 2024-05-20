package com.example.finalact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class DbManager {
    Context context;
    private final SQLiteDatabase db;

    private final String DBNAME = "appdb";
    private final int DBVER = 1;
    private final String TBLNAME = "students";
    private final String SID = "id";
    private final String STUNAME = "studentname";
    private final String PWD = "password";
    private final String ACCESS = "access";

    public DbManager(Context context) {
        this.context = context;
        CustomHelper helper = new CustomHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public void addRow(String name, String pwd, int access){
        ContentValues values = new ContentValues();
        values.put(STUNAME, name);
        values.put(PWD, pwd);
        values.put(ACCESS, access);
        try {
            db.insert(TBLNAME, null, values);
        } catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteRow(long id) {
        try {
            db.delete(TBLNAME, SID + "=" + id, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void updateRow(int access) {
        ContentValues values = new ContentValues();
        values.put(ACCESS, access);
        try {
            db.update(TBLNAME, values, ACCESS + " = " + access, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getRowAsArray(String n) {
        ArrayList<Object> rowArray = new ArrayList<>();
        Cursor cursor;

        try {
            cursor = db.query(
                    TBLNAME,
                    new String[] { SID, STUNAME, PWD },
                    STUNAME + " = " + "\"" + n + "\"",
                    null, null, null, null, null
                    );
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    rowArray.add(cursor.getLong(0));
                    rowArray.add(cursor.getString(1));
                    rowArray.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return rowArray;
    }

    public ArrayList<ArrayList<Object>> getAllRowsAsArrays(int access)
    {
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
        Cursor cursor;
        try {
            cursor = db.query(
                    TBLNAME,
                    new String[]{SID, STUNAME},
                    ACCESS + " = " + access,
                    null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cursor.getLong(0));
                    dataList.add(cursor.getString(1));
                    dataArrays.add(dataList);
                }
                while
                (cursor.moveToNext());
            }
            cursor.close();
        } catch(SQLException e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
        return dataArrays;
    }

    private class CustomHelper extends SQLiteOpenHelper {
        public CustomHelper(Context context){
            super(context, DBNAME, null, DBVER);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + TBLNAME + "(" + SID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + STUNAME + " TEXT, " + PWD + " TEXT, " + ACCESS + " INTEGER);";
            db.execSQL(sql);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerison) {
            db.execSQL("DROP TABLE IF EXISTS " + TBLNAME);
            onCreate(db);
        }

    }
}
