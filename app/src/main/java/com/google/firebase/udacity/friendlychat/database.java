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
    public static final String table_stu = "students";
    public static final String table_lec = "lecturers";
    public static final String table_Cmod = "Cmodules";  //Electronic eng
    public static final String table_Dmod = "Dmodules"; //Computer eng
    public static final String table_CDmod = "CDmodules"; //Electronic & Computer eng
    public static final String first_name = "first_name";
    public static final String last_name = "last_name";
    public static final String e_mail = "e_mail";
    public static final String password = "password";
    public static final String course = "course";
    public static final String module = "module";

    public database(Context context) {
        super(context, database_name, null, 1);
    }
    //students table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //students table
        db.execSQL("create table "+table_stu+"(first_name text, last_name text, e_mail text primary key, password text, course text foreign key");

        /* maybe not needed yet
        //lecturers table
        db.execSQL("create table "+table_lec+"(first_name text, last_name text, e_mail text primary key, password text"); */

        //Electronic Eng module table
        db.execSQL("create table "+table_Cmod+"(module text primary key, course text");

        //Computer Eng module table
        db.execSQL("create table "+table_Dmod+"(module text primary key, course text");

        //Electronic & Computer Eng module table
        db.execSQL("create table "+table_CDmod+"(module text primary key, course text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop old tables
        db.execSQL("drop table if exists " +table_stu);
       /* db.execSQL("drop table if exists " +table_lec);  maybe not needed yet */
        db.execSQL("drop table if exists " +table_Cmod);
        db.execSQL("drop table if exists " +table_Dmod);
        db.execSQL("drop table if exists " +table_CDmod);

        //create new tables
        onCreate(db);
    }

    //insert values to students table
    public boolean insert_student(String first_name, String last_name, String e_mail, String password, String course){
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
    };*/

    //insert values to Electronic Eng module table
    public boolean insert_module(String module, String course){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into Cmodules values ('"+module+"', '"+course+"')";
        Cursor cursor=db.rawQuery(query, null);

        cursor.close();

        return true;
    }

    //insert values to Computer Eng module table
    public boolean insert_module(String module, String course){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into Dmodules values ('"+module+"', '"+course+"')";
        Cursor cursor=db.rawQuery(query, null);

        cursor.close();

        return true;
    }

    //insert values to Electronic & Computer Eng module table
    public boolean insert_module(String module, String course){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into CDmodules values ('"+module+"', '"+course+"')";
        Cursor cursor=db.rawQuery(query, null);

        cursor.close();

        return true;
    }

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

    /* maybe not needed yet
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
