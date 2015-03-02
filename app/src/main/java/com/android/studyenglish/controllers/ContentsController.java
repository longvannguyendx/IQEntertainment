package com.android.studyenglish.controllers;

import android.content.Context;
import android.database.Cursor;

import com.android.studyenglish.models.Contents;

/**
 * Created by My PC on 27/02/2015.
 */
public class ContentsController {

    Context context = null;
    public ContentsController(Context context)
    {
        this.context = context;
    }
    public Contents getDataById(int Id)
    {
        DatabaseManager databaseManager = new DatabaseManager(context);
        if (databaseManager.openDataBase())
        {
           String sqlQuery = "Select * from " + Contents.getTableName() + " where Id =" + Id ;
            Cursor cursor;
            Contents contents = new Contents();
            cursor = databaseManager.getConnection().rawQuery(sqlQuery, null);

            if (cursor != null)
            {
                cursor.moveToFirst();
                contents.setId(cursor.getInt(0));
                contents.setPath(cursor.getString(1));
                contents.setAnswer(cursor.getInt(2));
                cursor.close();
                databaseManager.closeDatabase();
                return contents;
            }
            else
            {
                databaseManager.closeDatabase();
                return null;
            }
        }
        return null;
    }
}
