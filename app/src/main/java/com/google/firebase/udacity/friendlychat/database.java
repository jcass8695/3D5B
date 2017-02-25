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
        db.execSQL("create table "+table_stu+"(first_name text, last_name text, e_mail text primary key, password text, course text");

        /* maybe not needed yet
        //lecturers table
        db.execSQL("create table "+table_lec+"(first_name text, last_name text, e_mail text primary key, password text"); */

        //Electronic Eng module table
        db.execSQL("create table "+table_Cmod+"(module text primary key");

        //Computer Eng module table
        db.execSQL("create table "+table_Dmod+"(module text primary key");

        //Electronic & Computer Eng module table
        db.execSQL("create table "+table_CDmod+"(module text primary key");
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

    //function to insert values to Electronic Eng module table
    public boolean insert_Cmodule(String module){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into modules values ('"+module+"')";
        Cursor cursor=db.rawQuery(query, null);
        cursor.close();

        return true;
    }

    //function to insert values to Computer Eng module table
    public boolean insert_Dmodule(String module){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into modules values ('"+module+"')";
        Cursor cursor=db.rawQuery(query, null);
        cursor.close();

        return true;
    }

    //function to insert values to Electronic & Computer Eng module table
    public void insert_CDmodule(String module){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="insert into modules values ('"+module+"')";
        Cursor cursor=db.rawQuery(query, null);
        cursor.close();


    }

    public void insert_modules() {
        //add Electronic Eng modules
        insert_Cmodule("Engineering Mathematics V");
        insert_Cmodule("Probability and Statistics");
        insert_Cmodule("Innovation and Entrepreneurship for Engineers");
        insert_Cmodule("Signals and Systems");
        insert_Cmodule("Digital Circuits");
        insert_Cmodule("Microprocessor Systems I");
        insert_Cmodule("Data Structures and Algorithms");
        insert_Cmodule("Analogue Circuits");
        insert_Cmodule("Electronic Engineering Project A");
        insert_Cmodule("Telecommunnications");
        insert_Cmodule("Digital Logic Design");

        //add Computer Eng modules
        insert_Dmodule("Engineering Mathematics V");
        insert_Dmodule("Probability and Statistics");
        insert_Dmodule("Innovation and Entrepreneurship for Engineers");
        insert_Dmodule("Signals and Systems");
        insert_Dmodule("Digital Circuits");
        insert_Dmodule("Data Structures and Algorithms");
        insert_Dmodule("Microprocessor Systems I");
        insert_Dmodule("Microprocessor Systems II");
        insert_Dmodule("Computer Architecture II");
        insert_Dmodule("Operating Systems and Concurrent Systems");
        insert_Dmodule("Computer Networks");
        insert_Dmodule("Software Design and Implementation");


        //add Electronic & Computer Eng modules
        insert_CDmodule("Engineering Mathematics V");
        insert_CDmodule("Probability and Statistics");
        insert_CDmodule("Innovation and Entrepreneurship for Engineers");
        insert_CDmodule("Signals and Systems");
        insert_CDmodule("Digital Circuits");
        insert_CDmodule("Data Structures and Algorithms");
        insert_CDmodule("Microprocessor Systems I");
        insert_CDmodule("Telecommunnications");
        insert_CDmodule("Digital Logic Design");
        insert_CDmodule("Microprocessor Systems II");
        insert_CDmodule("Computer Networks");
        insert_CDmodule("Electronic Engineering Project B");
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
