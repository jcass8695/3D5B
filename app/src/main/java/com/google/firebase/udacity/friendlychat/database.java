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
    public void insert_Cmodule(String module){
        ContentValues modules = new ContentValues(11);
        modules.put("module", module);
        getWritableDatabase().insert("module", modules);
    }

    //function to insert values to Computer Eng module table
    public void insert_Dmodule(String module){
        ContentValues modules = new ContentValues(12);
        modules.put("module", module);
        getWritableDatabase().insert("module", modules);
    }

    //function to insert values to Electronic & Computer Eng module table
    public void insert_CDmodule(String module){
        ContentValues modules = new ContentValues(12);
        modules.put("module", module);
        getWritableDatabase().insert("module", modules);
    }

   /* //actually inserting the values into the module tables
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SQLiteOpenHelper = new SQLiteOpenHelper(this) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //add Electronic Eng modules
                SQLiteOpenHelper.insert_Cmodule("Engineering Mathematics V");
                SQLiteOpenHelper.insert_Cmodule("Probability and Statistics");
                SQLiteOpenHelper.insert_Cmodule("Innovation and Entrepreneurship for Engineers");
                SQLiteOpenHelper.insert_Cmodule("Signals and Systems");
                SQLiteOpenHelper.insert_Cmodule("Digital Circuits");
                SQLiteOpenHelper.insert_Cmodule("Microprocessor Systems I");
                SQLiteOpenHelper.insert_Cmodule("Data Structures and Algorithms");
                SQLiteOpenHelper.insert_Cmodule("Analogue Circuits");
                SQLiteOpenHelper.insert_Cmodule("Electronic Engineering Project B");
                SQLiteOpenHelper.insert_Cmodule("Telecommunnications");
                SQLiteOpenHelper.insert_Cmodule("Digital Logic Design");

                //add Computer Eng modules
                SQLiteOpenHelper.insert_Dmodule("Engineering Mathematics V");
                SQLiteOpenHelper.insert_Dmodule("Probability and Statistics");
                SQLiteOpenHelper.insert_Dmodule("Innovation and Entrepreneurship for Engineers");
                SQLiteOpenHelper.insert_Dmodule("Signals and Systems");
                SQLiteOpenHelper.insert_Dmodule("Digital Circuits");
                SQLiteOpenHelper.insert_Dmodule("Data Structures and Algorithms");
                SQLiteOpenHelper.insert_Dmodule("Microprocessor Systems I");
                SQLiteOpenHelper.insert_Dmodule("Microprocessor Systems II");
                SQLiteOpenHelper.insert_Dmodule("Computer Architecture II");
                SQLiteOpenHelper.insert_Dmodule("Operating Systems and Concurrent Systems");

                //add Electronic & Computer Eng modules
                SQLiteOpenHelper.insert_CDmodule("Engineering Mathematics V");
                SQLiteOpenHelper.insert_CDmodule("Probability and Statistics");
                SQLiteOpenHelper.insert_CDmodule("Innovation and Entrepreneurship for Engineers");
                SQLiteOpenHelper.insert_CDmodule("Signals and Systems");
                SQLiteOpenHelper.insert_CDmodule("Digital Circuits");
                SQLiteOpenHelper.insert_CDmodule("Data Structures and Algorithms");
                SQLiteOpenHelper.insert_CDmodule("Microprocessor Systems I");
                SQLiteOpenHelper.insert_CDmodule("Telecommunnications");
                SQLiteOpenHelper.insert_CDmodule("Digital Logic Design");
                SQLiteOpenHelper.insert_CDmodule("Microprocessor Systems II");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };


    }*/

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
