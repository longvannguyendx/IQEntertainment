package com.android.studyenglish.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.studyenglish.models.Contents;

/**
 * Created by My PC on 27/02/2015.
 */
public class SharedPreferencesManager {

    Context context;
    public  SharedPreferencesManager (Context context){
        this.context = context;
    }
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String m_Key = "numberKey";
    public static final String m_Score = "scoreKey";

    public void saveData(String key, int value)
    {
        SharedPreferences sf = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt(m_Key, value);
        editor.commit();
    }
    public int getData(String key)
    {
        SharedPreferences sf = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sf.contains(key))
        {
            return sf.getInt(key, 0);
        }
        return 0;
    }

}
