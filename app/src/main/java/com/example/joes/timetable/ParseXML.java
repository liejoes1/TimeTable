package com.example.joes.timetable;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

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

    public static void ReadFile() throws IOException {
        File FileDirectory = new File(Utils.getRootDirPath(appContext) + "/Timetable/");
        File FinalFileNameFile = new File(FileDirectory, FileDirectory.listFiles()[0].getName());


        //After Read XML File, Convert it to JSON
        FileInputStream fileInputStream = new FileInputStream(FinalFileNameFile);
        XmlToJson xmlToJson = new XmlToJson.Builder(fileInputStream, null).build();

        String JsonResult = xmlToJson.toString();
        Log.i("Files", "Filename" + JsonResult);



        try {
            JSONObject jsonObject = new JSONObject(JsonResult);

            JSONObject WeekObject = jsonObject.getJSONObject("weekof");

            JSONArray IntakeArray = WeekObject.getJSONArray("intake");

            for (int i = 0; i < IntakeArray.length(); i++) {
                JSONObject TimeTableObject = IntakeArray.getJSONObject(i);
                if (TimeTableObject.getString("name").equals("UC3F1801TC")) {
                    Log.i("TAG", "Intake Found12" + TimeTableObject.getString("name"));

                    JSONArray TimeTableArray = TimeTableObject.getJSONArray("timetable");
                    int Total_Timetable = TimeTableArray.length();
                    Log.i("TAG", "Length: " + Total_Timetable);
                    for (int a = 0; a < Total_Timetable; a++) {
                        JSONObject TimeTableIndividualObject = TimeTableArray.getJSONObject(a);
                        Timetable timeTable = new Timetable();
                        timeTable.setDate(TimeTableIndividualObject.getString("date"));
                        timeTable.setTime(TimeTableIndividualObject.getString("time"));
                        timeTable.setLocation(TimeTableIndividualObject.getString("location"));
                        timeTable.setClassroom(TimeTableIndividualObject.getString("classroom"));
                        timeTable.setModule(TimeTableIndividualObject.getString("module"));
                        timeTable.setLecturer(TimeTableIndividualObject.getString("lecturer"));
                        Utils.timetableList.add(timeTable);

                    }
                    for (int c = 0; c < Utils.timetableList.size(); c++) {
                        String classroom =  Utils.timetableList.get(c).getClassroom();
                        String date =  Utils.timetableList.get(c).getDate();
                        String time =  Utils.timetableList.get(c).getTime();
                        String location =  Utils.timetableList.get(c).getLocation();
                        String module =  Utils.timetableList.get(c).getModule();
                        String lecturer =  Utils.timetableList.get(c).getLecturer();

                        Log.i("TAG", "Result Date" + date);
                        Log.i("TAG", "Result Time" + time);
                        Log.i("TAG", "Result Location" + location);
                        Log.i("TAG", "Result Classroom" + classroom);
                        Log.i("TAG", "Result Module" + module);
                        Log.i("TAG", "Result Lecturer" + lecturer);
                    }

                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

