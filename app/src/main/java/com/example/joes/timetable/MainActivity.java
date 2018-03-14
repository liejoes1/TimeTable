package com.example.joes.timetable;


import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    public static TextView TextViewStartDownload, TextViewDownloadSuccess,TextViewDownloadFailed;
    private Button ButtonRetry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkActivity.setContext(getApplicationContext(), this);
        ParseXML.setContext(getApplicationContext(), this);
        init();
        try {
            ParseXML.ReadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ButtonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextViewStartDownload.setVisibility(View.GONE);
                TextViewDownloadSuccess.setVisibility(View.GONE);
                TextViewDownloadFailed.setVisibility(View.GONE);
                NetworkActivity.startDownload();
            }
        });

    }

    private void init() {
        TextViewStartDownload = findViewById(R.id.TextViewStartDownload);
        TextViewDownloadSuccess = findViewById(R.id.TextViewDownloadSuccess);
        TextViewDownloadFailed = findViewById(R.id.TextViewDownloadFailed);

        ButtonRetry = findViewById(R.id.ButtonRetry);
    }


}
