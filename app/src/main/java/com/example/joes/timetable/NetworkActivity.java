package com.example.joes.timetable;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static android.view.View.GONE;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.joes.timetable.ParseXML.ParseTimeTableList;


/**
 * Created by Joes on 13/3/2018.
 */

public class NetworkActivity {


    private static Context appContext;
    private static String ROOT_DIRECTORY_PATH;
    private static String ROOT_TEMP_PATH;

    private static final String TIMETABLE_LIST_URL = "https://lms.apiit.edu.my/intake-timetable/TimetableIntakeList/TimetableIntakeList.xml";
    private static String TIMETABLE_INFO_BASE = "https://webspace.apiit.edu.my/intake-timetable/replyLink.php?stid=";


    public static void setContext(Context mappContext) {
        appContext = mappContext;
    }


    public static void startDownload() {
        ROOT_DIRECTORY_PATH = Utils.getRootDirPath(appContext);
        File TempDir = new File(ROOT_DIRECTORY_PATH, "/temp");
        if (!TempDir.exists()) {
            TempDir.mkdirs();
        }
        ROOT_TEMP_PATH = TempDir.toString();
        MainActivity.RelativeLayoutDownload.setVisibility(View.VISIBLE);
        new GetTimeTableListAsyncTask().execute();
    }


    private static class GetTimeTableListAsyncTask extends AsyncTask<Void, Void, Void> {

        File TempFile = new File(ROOT_TEMP_PATH, "TimeTableListTemporary.xml");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.RelativeLayoutDownload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL(TIMETABLE_LIST_URL);
                FileOutputStream fileOutputStream = new FileOutputStream(TempFile);
                InputStream inputStream = getNetworkData(url).getInputStream();
                int lenghtOfFile = getNetworkData(url).getContentLength();

                byte data[] = new byte[1024];
                int count = 0;
                long total = 0;
                int progress = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    int progress_temp = (int) total * 100 / lenghtOfFile;
                    if (progress_temp % 10 == 0 && progress != progress_temp) {
                        progress = progress_temp;
                    }
                    fileOutputStream.write(data, 0, count);
                }
                inputStream.close();
                fileOutputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainActivity.RelativeLayoutDownload.setVisibility(GONE);
            File NewFile = new File(ROOT_DIRECTORY_PATH, "TimeTableList.xml");
            System.out.println("New File: " + ROOT_DIRECTORY_PATH);
            //Copy File from TEMP Folder to ROOT Folder
            try {
                InputStream inputStream = new FileInputStream(TempFile);
                OutputStream outputStream = new FileOutputStream(NewFile);

                byte[] bytes = new byte[1024];
                int lengthOfFile;
                while ((lengthOfFile = inputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, lengthOfFile);
                }
                inputStream.close();
                outputStream.close();
                ParseXML.ParseTimeTableList(new FileInputStream(NewFile));
                MainActivity.autoCompleteTextView.setAdapter(ParseXML.getIntakeList(appContext));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class GetTimeTableInfoAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(TIMETABLE_INFO_BASE + strings[0]);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                String fullString = "";
                while ((str = bufferedReader.readLine()) != null) {
                    fullString += str;
                }
                bufferedReader.close();
                Log.i("LOG", "Timetable Info: " + fullString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static HttpURLConnection getNetworkData(URL url) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

}


