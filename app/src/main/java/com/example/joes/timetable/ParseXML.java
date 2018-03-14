package com.example.joes.timetable;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String line = null;

        while ((line = reader.readLine()) != null) {
            buff.append(line).append("\n");
        }
        reader.close();
        Log.i("Files", "Filename" + buff);
    }
}
