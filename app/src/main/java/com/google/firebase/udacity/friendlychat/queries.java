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

        //run query to get modules
        if (course=="Electronic Engineering") {
            String query = "select * from " + table_Cmod + " where course='" + course + "'";
        }
        else if (course=="Computer Engineering"){
            String query = "select * from " + table_Dmod + " where course='" + course + "'";
        }
        else if (course=="Electronic and Computer Engineering"){
            String query = "select * from " + table_CDmod + " where course='" + course + "'";
        }
        Cursor cursor = db.rawQuery(query, null);

        //loop through the rows
        if (cursor.moveToFirst()) {
            do {
                String module = cursor.getString(cursor.);
                List.add(module);
                cursor.moveToNext();
            }
            while (cursor.isAfterLast()==false);
            }

        cursor.close();

        //return array of modules
        return moduleList;
    }

}
