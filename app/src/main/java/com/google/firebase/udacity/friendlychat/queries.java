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

    public String getCourse(){
        SQLiteDatabase db=this.getWritableDatabase();
        //run query to get student's course
        String query="select * from students where e_mail='"+e_mail+"'";
        //store course in a variable
        public String course;
        //return the course as a string
        return course;
    }

    public int getModules(){
        SQLiteDatabase db=this.getWritableDatabase();
        public int numRows = db.getCount(); //find how many rows are in the database
        public int loopCounter = 0; //counter for loop
        public int moduleCounter = 0; //counts number of modules found
        public String[] modules = new String[12]; //array to hold modules

        //check all rows, return all modules associated with that course
        while (loopCounter<=numRows){
            //run query to get each module
            String query="select * from modules where course='"+course+"'";

            if() {
                //if we find a corresponding module, place it in the array
                modules[moduleCounter] = ;
                moduleCounter++;
            }

            //increment loop counter
            loopCounter++;
        }

    };

}
