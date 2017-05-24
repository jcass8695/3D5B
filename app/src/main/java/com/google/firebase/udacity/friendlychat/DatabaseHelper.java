package com.google.firebase.udacity.friendlychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2/24/2017.
 */

public final class DatabaseHelper extends SQLiteOpenHelper {
    public static final String database_name = "database_10";
    public static final String table_user = "users";
    public static final String table_Dmod = "Computer_Engineering";
    public static final String table_CDmod = "Electronics_and_Computer_Engineering";
    public static final String table_Cmod = "Electronics_Engineering";

    public static final String COL_1 = "first_name";
    public static final String COL_2 = "last_name";
    public static final String COL_3 = "e_mail";
    public static final String COL_4 = "password";
    public static final String COL_5 = "user";
    public static final String COL_6 = "department";

    public static final String module = "modules";

    public DatabaseHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table_user+"(first_name TEXT, last_name TEXT, e_mail TEXT PRIMARY KEY, password TEXT, user TEXT, department TEXT)");
        db.execSQL("create table "+table_Dmod+"(modules TEXT PRIMARY KEY)");
        db.execSQL("create table "+table_CDmod+"(modules TEXT PRIMARY KEY)");
        db.execSQL("create table "+table_Cmod+"(modules TEXT PRIMARY KEY)");

        db.execSQL("insert into "+table_CDmod+" values ('3E4')");
        db.execSQL("insert into "+table_CDmod+" values ('3C5')");
        db.execSQL("insert into "+table_CDmod+" values ('3C7')");
        db.execSQL("insert into "+table_CDmod+" values ('3D2')");
        db.execSQL("insert into "+table_CDmod+" values ('3D3')");
        db.execSQL("insert into "+table_CDmod+" values ('3C6B')");

        db.execSQL("insert into "+table_Dmod+" values ('3E4')");
        db.execSQL("insert into "+table_Dmod+" values ('3D2')");
        db.execSQL("insert into "+table_Dmod+" values ('CS2022')");
        db.execSQL("insert into "+table_Dmod+" values ('3D4')");
        db.execSQL("insert into "+table_Dmod+" values ('3D3')");
        db.execSQL("insert into "+table_Dmod+" values ('3D5B')");

        db.execSQL("insert into "+table_Cmod+" values ('3C3')");
        db.execSQL("insert into "+table_Cmod+" values ('3C6A')");
        db.execSQL("insert into "+table_Cmod+" values ('3C5')");
        db.execSQL("insert into "+table_Cmod+" values ('3C7')");
        db.execSQL("insert into "+table_Cmod+" values ('3E4')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_user);
        db.execSQL("DROP TABLE IF EXISTS "+table_Dmod);
        db.execSQL("DROP TABLE IF EXISTS "+table_Cmod);
        db.execSQL("DROP TABLE IF EXISTS "+table_CDmod);
        onCreate(db);
    }

    public boolean insert_users(String first_name, String last_name, String e_mail, String password, String user, String department){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, first_name);
        contentValues.put(COL_2, last_name);
        contentValues.put(COL_3, e_mail);
        contentValues.put(COL_4, password);
        contentValues.put(COL_5, user);
        contentValues.put(COL_6, department);
        long result=db.insert(table_user, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor check_users(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+table_user+" where e_mail='"+email+"'", null);
        return res;
    }

    public Cursor get_all_users(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+table_user, null);
        return res;
    }

    public Cursor get_all_modules(String table){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+table, null);
        return res;
    }

}