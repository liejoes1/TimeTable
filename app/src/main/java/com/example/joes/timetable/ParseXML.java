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
        String FileName = null;
        File[] FileNameArray = FileDirectory.listFiles();

        FileName = FileNameArray[0].getName();
        File FinalFileNameFile = new File(FileDirectory, FileName);


        FileInputStream  fileInputStream = new FileInputStream(FinalFileNameFile);
        InputStreamReader isr = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        Log.i("Files", "Filename" + sb.toString());

        //After Read XML File, Convert it to JSON
        fileInputStream = new FileInputStream(FinalFileNameFile);
        XmlToJson xmlToJson = new XmlToJson.Builder(fileInputStream, null).build();

        fileInputStream.close();
        String JsonResult = xmlToJson.toString();
        Log.i("Files", "Filename" + JsonResult);

        File myFile = new File(Utils.getRootDirPath(appContext) + "/Timetable/", "Timetable1.xml");

        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append(sb.toString());
        myOutWriter.close();
        fOut.close();


        try {
            JSONObject jsonObject = new JSONObject(JsonResult);

            JSONObject WeekObject = jsonObject.getJSONObject("weekof");

            JSONArray IntakeArray = WeekObject.getJSONArray("intake");

            for (int i = 0; i < IntakeArray.length(); i++) {
                JSONObject TimeTableObject = IntakeArray.getJSONObject(i);
                Log.i("TAG", "Intake Found12" + TimeTableObject.getString("name"));
                if (TimeTableObject.getString("name").equals("UC2F1711TRM")) {

                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

