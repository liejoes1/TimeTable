package com.example.joes.timetable;


import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public static LinearLayout SplashScreenLinearLayout, LoadingScreenLinearLayout;
    public static RelativeLayout IntakeScreenRelativeLayout, IntakeScreenErrorChoiceRelativeLayout, IntakeScreenErrorInvalidRelativeLayout;

    public static TextView ContinueButtonTextView, OKButton1TextView, OKButton2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkActivity.setContext(getApplicationContext());
        ParseXML.setContext(getApplicationContext(), this);
        init();
        NetworkActivity.startDownload();


        ContinueButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Utils.ListOfAllIntake.size(); i++) {
                    if (autoCompleteTextView.getText().toString().equals(Utils.ListOfAllIntake.get(i))) {
                        Log.i("IntakeResult", "Result: " + autoCompleteTextView.getText().toString());
                        HideKeyboard();
                        new NetworkActivity.GetTimeTableInfoAsyncTask().execute(autoCompleteTextView.getText().toString());
                        return;
                    }
                }
                Log.i("IntakeResult", "Result: " + autoCompleteTextView.getText().toString());
                IntakeScreenErrorChoiceRelativeLayout.setVisibility(View.VISIBLE);
            }


        });

        OKButton1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntakeScreenErrorChoiceRelativeLayout.setVisibility(View.GONE);
                IntakeScreenRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        OKButton2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntakeScreenErrorInvalidRelativeLayout.setVisibility(View.GONE);
                IntakeScreenRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        mAdapter = new TimeTableAdapter(this, Utils.timetableList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);



    }


    private void HideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_timetable);
        SplashScreenLinearLayout = (LinearLayout) findViewById(R.id.ll_splash_screen);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.TextViewTimeTableList);
        IntakeScreenRelativeLayout = (RelativeLayout) findViewById(R.id.rl_intake_screen);
        ContinueButtonTextView = (TextView) findViewById(R.id.tv_continue_button);
        IntakeScreenErrorChoiceRelativeLayout = (RelativeLayout) findViewById(R.id.rl_intake_screen_error_choice);

        OKButton1TextView = (TextView) findViewById(R.id.tv_OK_button_1);

        IntakeScreenErrorInvalidRelativeLayout = (RelativeLayout) findViewById(R.id.rl_intake_screen_error_invalid);
        OKButton2TextView = (TextView) findViewById(R.id.tv_OK_button_2);

        LoadingScreenLinearLayout = (LinearLayout) findViewById(R.id.ll_loading_screen);
    }


}
