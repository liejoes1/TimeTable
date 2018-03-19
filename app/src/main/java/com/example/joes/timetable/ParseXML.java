package com.example.joes.timetable;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;


/**
 * Created by Joes on 14/3/2018.
 */

public class ParseXML {
    private static Context appContext;
    private static Context mainContext;

    private static final String ns = null;

    public static void setContext(Context mappContext, Context mmainContext) {
        appContext = mappContext;
        mainContext = mmainContext;
    }


    public static void ParseTimeTableList(FileInputStream xmlFileName) {
        try {
            XmlToJson xmlToJson = new XmlToJson.Builder(xmlFileName, null).build();
            JSONObject RootObject = new JSONObject(xmlToJson.toString());
            JSONObject WeekOfObject = RootObject.getJSONObject("weekof");
            JSONArray IntakeArray = WeekOfObject.getJSONArray("intake");

            for (int CurrentIntakeIndex = 0; CurrentIntakeIndex < IntakeArray.length(); CurrentIntakeIndex++) {
                JSONObject EachIntake = IntakeArray.getJSONObject(CurrentIntakeIndex);
                Utils.ListOfAllIntake.add(CurrentIntakeIndex, EachIntake.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void ParseTimeTable(FileInputStream xmlFileName) {
        XmlToJson xmlToJson = new XmlToJson.Builder(xmlFileName, null).build();

        try {
            JSONObject RootObject = new JSONObject(xmlToJson.toString());
            JSONObject WeekObject = RootObject.getJSONObject("week");
            JSONArray DayArray = WeekObject.getJSONArray("day");
            Log.i("LOG", "Parse Timetable: " + DayArray);

            for (int DayIndex = 0; DayIndex < DayArray.length(); DayIndex++) {
                JSONObject DailyObject = DayArray.getJSONObject(DayIndex);
                JSONArray ClassArray = DailyObject.getJSONArray("class");
                for (int DailyArrayIndex = 0; DailyArrayIndex < ClassArray.length(); DailyArrayIndex++) {
                    JSONObject SubjectObject = ClassArray.getJSONObject(DailyArrayIndex);
                    String Module = SubjectObject.getString("subject");
                    String Classroom = SubjectObject.getString("location");
                    String StartTime = SubjectObject.getString("start");
                    String EndTime = SubjectObject.getString("end");
                    String Date = StartTime.substring(0, 10);

                    Utils.timetableList.add(new Timetable(Date, StartTime, EndTime, Classroom, Module));
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayAdapter<String> getIntakeList(Context context) {
        String[] addresses = new String[Utils.ListOfAllIntake.size()];

        for (int i = 0; i < Utils.ListOfAllIntake.size(); i++) {
            addresses[i] = Utils.ListOfAllIntake.get(i);
            Log.i("LOG", "Parse Result: " + Utils.ListOfAllIntake.get(i));
        }

        return new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, addresses);
    }
}

