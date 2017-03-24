package com.google.firebase.udacity.friendlychat;

import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aoife on 16/02/2017.
 */

public final class chat_database extends SQLiteOpenHelper{

    public static final String database_name = "course_db";
    public static final String table_Cmod = "Cmodules";  //Electronic eng
    public static final String table_Dmod = "Dmodules"; //Computer eng
    public static final String table_CDmod = "CDmodules"; //Electronic & Computer eng
    public static final String course = "course";
    public static final String module = "module";

    public chat_database(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase course_db) {
        //Electronic Eng module table
        course_db.execSQL("create table "+table_Cmod+"(module text primary key");

        //Computer Eng module table
        course_db.execSQL("create table "+table_Dmod+"(module text primary key");

        //Electronic & Computer Eng module table
        course_db.execSQL("create table "+table_CDmod+"(module text primary key");
    }

    @Override
    public void onUpgrade(SQLiteDatabase course_db, int oldVersion, int newVersion) {
        course_db.execSQL("drop table if exists " +table_Cmod);
        course_db.execSQL("drop table if exists " +table_Dmod);
        course_db.execSQL("drop table if exists " +table_CDmod);

        //create new tables
        onCreate(course_db);
    }

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


    public class queries{

        //retrieve the student's course
        public String getCourse(String e_mail){
            SQLiteDatabase db=this.getWritableDatabase();
            //run query to get student's course
            String query="select * from "+table_stu+" where e_mail='"+e_mail+"'";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToLast();
            //store course in a variable
            String course = cursor.getString(5);

            cursor.close();
            //return the course as a string
            return course;
        }

        //retrieve the modules associated with that course
        public List<modules> getModules(String course){

            List<modules> moduleList = new ArrayList<modules>();
            SQLiteDatabase db = this.getWritableDatabase();
            String query;
            //run query to get modules
            if (course.equals("Electronic Engineering")) {
                query = "select * from " + table_Cmod + " where course='" + course + "'";
            }
            else if (course.equals("Computer Engineering")){
                query = "select * from " + table_Dmod + " where course='" + course + "'";
            }
            else if (course.equals("Electronic and Computer Engineering")){
                query = "select * from " + table_CDmod + " where course='" + course + "'";
            }

            Cursor cursor = db.rawQuery(query, null);
            //loop through the rows
            if (cursor.moveToFirst()) {
                do {
                    String module = cursor.getString(cursor.);
                    List.add(module);
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }

            cursor.close();

            //return array of modules
            return moduleList;
        }

    }

}
