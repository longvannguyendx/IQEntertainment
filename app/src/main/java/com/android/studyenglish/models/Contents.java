package com.android.studyenglish.models;

/**
 * Created by My PC on 27/02/2015.
 */
public class Contents {

    private static String m_TableName = "Contents";
    private int m_Id = 0;
    private String m_Path = null;
    private int m_Answer = 0;

    public static String getTableName() {
        return m_TableName;
    }

    public int getId() {
        return m_Id;
    }


    public String getPath() {
        return m_Path;
    }

    public int getAnswer() {
        return m_Answer;
    }

    public void setId(int id) {
        m_Id = id;
    }


    public void setPath(String path) {
        m_Path = path;
    }

    public void setAnswer(int answer) {
        m_Answer = answer;
    }
}
