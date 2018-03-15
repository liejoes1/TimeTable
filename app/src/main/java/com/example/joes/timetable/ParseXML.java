package com.example.joes.timetable;

import android.content.Context;
import android.util.Log;

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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by Joes on 14/3/2018.
 */

public class ParseXML {
    private static Context appContext;
    private static Context mainContext;

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



        StringBuffer buff = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(FinalFileNameFile));
        String line;


        while ((line = reader.readLine()) != null) {
            buff.append(line).append("\n");
        }
        reader.close();
        Log.i("Files", "Filename" + buff);

        String FinalXMLString = buff.toString();

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

            xmlPullParser.setInput( new InputStreamReader(new FileInputStream(FinalFileNameFile)));
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start Document");
                }
                else if (eventType == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("intake")) {
                        if (xmlPullParser.getAttributeValue(null, "name").equals("UC2F1708SE")) {
                            Log.i("TAG ", "Intake Found12");
                            break;
                        }
                    }



                }
                eventType = xmlPullParser.next();
            }
            System.out.println("End document");





        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


    }
}
