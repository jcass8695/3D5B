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

/*
Doesn't actually need to be its own activity I just wanted to work on it in a separate window.
Will merge with database activity when ready.
 */

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
       
        List<modules> moduleList = new ArrayList<modules>();  //create array as a list
        SQLiteDatabase db = this.getWritableDatabase();
        String query; //variable to hold our DB query
        
        //specify query
        if (course.equals("Electronic Engineering")) {
            query = "select * from " + table_Cmod + " where course='" + course + "'";
        }
        else if (course.equals("Computer Engineering")){
            query = "select * from " + table_Dmod + " where course='" + course + "'";
        }
        else if (course.equals("Electronic and Computer Engineering")){
            query = "select * from " + table_CDmod + " where course='" + course + "'";
        }

        Cursor cursor = db.rawQuery(query, null); //run the query
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