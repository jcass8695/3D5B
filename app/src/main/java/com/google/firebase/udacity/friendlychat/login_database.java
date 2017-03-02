package com.google.firebase.udacity.friendlychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

/**
 * Created by User on 2/9/2017.
 */

/* It is good practice to create a separate class per table.
    This class defines static onCreate() and onUpgrade() methods.
    These methods are called in the corresponding methods of SQLiteOpenHelper.
    This way your implementation of SQLiteOpenHelper stays readable, even if you have several tables.
*/

public final class login_database extends SQLiteOpenHelper {

    public static final String database_name = "chat_room";
    public static final String table_stu = "students";
    public static final String table_lec = "lecturers";
    public static final String first_name = "first_name";
    public static final String last_name = "last_name";
    public static final String e_mail = "e_mail";
    public static final String password = "password";


    public login_database(Context context) {
        super(context, database_name, null, 1);
    }
    //students table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //students table
        db.execSQL("create table "+table_stu+"(first_name text, last_name text, e_mail text primary key, password text, course text");

        /*
        //lecturers table
        db.execSQL("create table "+table_lec+"(first_name text, last_name text, e_mail text primary key, password text");

        //Electronic Eng module table
        db.execSQL("create table "+table_Cmod+"(module text primary key");

        //Computer Eng module table
        db.execSQL("create table "+table_Dmod+"(module text primary key");

        //Electronic & Computer Eng module table
        db.execSQL("create table "+table_CDmod+"(module text primary key");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop old tables
        db.execSQL("drop table if exists " +table_stu);
       /* db.execSQL("drop table if exists " +table_lec);
        db.execSQL("drop table if exists " +table_Cmod);
        db.execSQL("drop table if exists " +table_Dmod);
        db.execSQL("drop table if exists " +table_CDmod);*/

        //create new tables
        onCreate(db);
    }

    //insert values to students table
    public boolean insert(String first_name, String last_name, String e_mail, String password, String course){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into students values ('"+first_name+"', '"+last_name+"', '"+e_mail+"', '"+password+"', '"+course+"')";
        Cursor cursor=db.rawQuery(query, null);
        cursor.close();

        return true;
    }

    /* maybe not needed yet
    //insert values to lecturer table
    public boolean insert_lecturer(String first_name, String last_name, String e_mail, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into lecturers values ('"+first_name+"', '"+last_name+"', '"+e_mail+"', '"+password+"')";
        Cursor cursor=db.rawQuery(query, null);
        cursor.close();
        return true;
    };


    //student login authentication
    public boolean check_student(String e_mail, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from students where e_mail='"+e_mail+"'";
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToLast();
        String result=cursor.getString(4);
        if(result.equals(password)){
            cursor.close();
            return true;
        }
        else
            cursor.close();
            return false;
    }

    /*
    //lecturer login authentication
    public boolean check_lecturer(String e_mail, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from lecturers where e_mail='"+e_mail+"'";
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToLast();
        String result=cursor.getString(4);
        if(result.equals(password)){
            cursor.close();
            return true;
        }
        else
            cursor.close();
            return false;
    };*/
}
