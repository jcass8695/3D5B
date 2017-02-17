package com.google.firebase.udacity.friendlychat;

import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        //return the course as a string
        return course;
    };

    //retrieve the modules associated with that course
    public int getModules(String course){
        SQLiteDatabase db=this.getWritableDatabase();
        int moduleCounter = 0; //counts number of modules found
        String[] modules = new String[12]; //array to hold modules

        //check all rows, return all modules associated with that course
        while (moduleCounter<=12){
            //run query to get each module
            String query="select * from modules where course='"+course+"'";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToLast();
            //when we find a corresponding module, place it in the array
            modules[moduleCounter] = cursor.getString(1);
            //increment module counter
            moduleCounter++;
        }

    };

}
