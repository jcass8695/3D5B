package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2/9/2017.
 */

public final class database extends SQLiteOpenHelper {

    public static final String database_name = "chat_room";
    public static final String table_name = "students";
    public static final String first_name = "first_name";
    public static final String last_name = "last_name";
    public static final String e_mail = "e_mail";
    public static final String password = "password";
    public static final String course = "course";

    public database(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ table_name+"(first_name text, last_name text, e_mail text primary key, password text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert_student(String first_name, String last_name, String e_mail, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into students values ('"+first_name+"', '"+last_name+"', '"+e_mail+"', '"+password+"')";
        Cursor cursor=db.rawQuery(query, null);
        return true;
    };

    public boolean check_student(String e_mail, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from students where e_mail='"+e_mail+"'";
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToLast();
        String result=cursor.getString(4);
        if(result.equals(password)){
            return true;
        }
        else
            return false;
    };
}
