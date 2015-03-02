package com.android.studyenglish.views;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import com.android.studyenglish.controllers.ContentsController;
import com.android.studyenglish.controllers.SharedPreferencesManager;
import com.android.studyenglish.models.Contents;
import com.facebook.widget.LoginButton;

import java.lang.reflect.Array;
import java.util.Arrays;

import english.study.android.com.studyenglishapp.R;


public class MainActivity extends Activity implements View.OnClickListener {

    // Controls
    RadioGroup  m_GrpOption     = null;
    WebView     m_ImgContent    = null;
    Button      m_BtnIndex      = null;
    Button      m_BtnOk         = null;
    LoginButton m_btnLoginFB    = null;
    ProgressBar m_ProgressBar   = null;

    Contents                    m_Contents              = null;
    ContentsController          m_ContentsController    = null;
    SharedPreferencesManager    m_SharedPreferencesManager = null;
    final Activity activity = this;
    private void Init()
    {
        m_GrpOption = (RadioGroup) findViewById(R.id.grpOption);
        m_ImgContent = (WebView) findViewById(R.id.imgContent);
        m_BtnIndex  = (Button) findViewById(R.id.btnIndex);
        m_ProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        m_BtnOk = (Button)findViewById(R.id.btnOK);
        m_BtnOk.setOnClickListener(this);
        m_btnLoginFB = (LoginButton)findViewById(R.id.btnLoginFB);
        m_btnLoginFB.setPublishPermissions(Arrays.asList("public_profile"));
        Display display = getWindowManager().getDefaultDisplay();
        ViewGroup.LayoutParams layoutParams = m_ImgContent.getLayoutParams();
        double sizeWebview = display.getWidth()* 0.95;
        layoutParams.width = (int)sizeWebview;
        layoutParams.height = (int)sizeWebview;
        m_ImgContent.setLayoutParams(layoutParams);
        m_ImgContent.setBackgroundColor(Color.TRANSPARENT);


        m_ImgContent.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                m_ProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                m_ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                m_ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                m_ProgressBar.setVisibility(View.GONE);
            }
        });
        m_ContentsController = new ContentsController(this);
        m_SharedPreferencesManager = new SharedPreferencesManager(this);
        m_Contents = new Contents();

        loadQuestion();
    }

    private String createHtml(String urlImage)
    {
        String html = "<html>";
        html += "<style>";
        html += "*{";
        html +="padding: 0px;";
        html +="margin: 0px;";
        html +="}";
        html +="img {";
        html +="width: 100%;";
        html +="height: 100%;";
        html +="align: middle;";
        html +="border-radius: 10px;";
        html +="}";
        html +="</style>";
        html +="<body>";

        html +="<img src=" + "'" + urlImage + "'" + "/>";

        html +="</body>";
        html +="</html>";
        Log.i("luckyboy", "html: " + html);
        return html;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private boolean checkAnswer()
    {
        if (m_Contents != null)
        {
            int answer = m_Contents.getAnswer();
            int userAnswer = 0;
            switch (m_GrpOption.getCheckedRadioButtonId())
            {
                case R.id.optA:
                    userAnswer = 1;
                    break;
                case R.id.optB:
                    userAnswer = 2;
                    break;
                case R.id.optC:
                    userAnswer = 3;
                    break;
                case R.id.optD:
                    userAnswer = 4;
                    break;
            }
            m_GrpOption.clearCheck();
            if (answer == userAnswer)
            {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    private void loadQuestion()
    {
        int id = m_SharedPreferencesManager.getData(SharedPreferencesManager.m_Key);
        loadQuestion(id);
    }
    private void loadQuestion(int id)
    {
        m_Contents = m_ContentsController.getDataById((id == 0)? 1: id);

        if (m_Contents != null) {

            m_BtnIndex.setText(Integer.toString(m_Contents.getId()));
            String urlImage = m_Contents.getPath();
            m_ImgContent.loadData(createHtml(urlImage), "text/html; charset=UTF-8", null);

            // Save current question.
            int keyQuestion = Integer.parseInt(m_BtnIndex.getText().toString());
            m_SharedPreferencesManager.saveData(SharedPreferencesManager.m_Key, keyQuestion);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnOK:
                if (checkAnswer())
                {
                    loadQuestion(Integer.parseInt(m_BtnIndex.getText().toString())+1);
                }
                break;
        }
    }

    private class downLoadImageFromServer extends AsyncTask <String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //imgContent.setImageBitmap(bitmap);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
