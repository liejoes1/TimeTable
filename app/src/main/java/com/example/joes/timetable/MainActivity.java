package com.example.joes.timetable;


import android.app.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    public static RecyclerView recyclerView;
    public static TimeTableAdapter mAdapter;
    public static AutoCompleteTextView autoCompleteTextView;
    public Button SubmitButton;

    public static RelativeLayout RelativeLayoutDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkActivity.setContext(getApplicationContext());
        ParseXML.setContext(getApplicationContext(), this);
        init();
        NetworkActivity.startDownload();


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Utils.ListOfAllIntake.size(); i++) {
                    if (autoCompleteTextView.getText().toString().equals(Utils.ListOfAllIntake.get(i))) {
                        Log.i("IntakeResult", "Result: " + autoCompleteTextView.getText().toString());
                        new NetworkActivity.GetTimeTableInfoAsyncTask().execute(autoCompleteTextView.getText().toString());
                    }
                    else {
                        Log.i("IntakeResult", "Result: " + autoCompleteTextView.getText().toString());

                    }
                }


            }
        });
    }



    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_timetable);
        RelativeLayoutDownload = (RelativeLayout) findViewById(R.id.RelativeLayoutDownload);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.TextViewTimeTableList);
        SubmitButton = (Button) findViewById(R.id.SubmitButton);
    }





}
