package com.android.studyenglish.controllers;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by My PC on 27/02/2015.
 */
public class DatabaseManager {
    String m_PathDatabase = null;
    SQLiteDatabase m_Connection = null;
    private Context context = null;

    private void copyDatabaseFromAssets()
    {
        m_PathDatabase = context.getApplicationInfo().dataDir +"/IQOrdeal.s3db";
        File file = new File(m_PathDatabase);
        if (file.exists())
        {
            file.delete();
        }

        String m_PathAssets =  "IQOrdeal.s3db";
        try {
            InputStream in = context.getAssets().open(m_PathAssets);
            OutputStream out = new FileOutputStream(file);
            //OutputStream out = new FileOutputStream(m_PathDatabase);

            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            Log.i("luckyboy", "error: " + e.toString());
            e.printStackTrace();
        }


    }

    public DatabaseManager(Context context)
    {
        this.context = context;
        copyDatabaseFromAssets();
    }

    public SQLiteDatabase  getConnection()
    {
        return m_Connection;
    }
    public boolean openDataBase()
    {

        m_Connection = SQLiteDatabase.openDatabase(m_PathDatabase, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        if (m_Connection != null) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void closeDatabase()
    {
        if (m_Connection != null)
        {
            m_Connection.close();
        }
    }
}
