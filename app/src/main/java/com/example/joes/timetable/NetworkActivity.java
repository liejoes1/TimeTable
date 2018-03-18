package com.example.joes.timetable;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static android.view.View.GONE;
import static com.example.joes.timetable.MainActivity.recyclerView;


/**
 * Created by Joes on 13/3/2018.
 */

public class NetworkActivity {

    private static String DIRECTORY_PATH;
    private static Context appContext;
    private static Context mainContext;


    private static final String TIMETABLE_URL = "https://lms.apiit.edu.my/intake-timetable/download_timetable/timetableXML.zip";
    private static final String TIMETABLE_LIST_URL = "https://lms.apiit.edu.my/intake-timetable/TimetableIntakeList/TimetableIntakeList.xml";

    private static String JSON_STRING;
    public static void setContext(Context mappContext, Context mmainContext) {
        appContext = mappContext;
        mainContext = mmainContext;
    }
    public static void getValue(String jsonString) {
        JSON_STRING = jsonString;
        Log.i("LOG", "JSONTEST" + jsonString);
    }

    public static void startDownload() {
        DIRECTORY_PATH = Utils.getRootDirPath(appContext);

        MainActivity.RelativeLayoutDownload.setVisibility(View.VISIBLE);
        new GetTimeTableListAsyncTask().execute();

    }


    private static class GetTimeTableListAsyncTask extends AsyncTask<Void, Void, Void> {

        File FileDirectory = new File(Utils.getRootDirPath(appContext) + "/Timetable/");
        File FinalFileName = new File(FileDirectory, "TimetableListTemp.xml");

        File NewFileName = new File(FileDirectory, "TimetableList.xml");
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


                FileOutputStream fileOutputStream = new FileOutputStream(FinalFileName);

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

            File[] ListOfFile = FileDirectory.listFiles();
            NewFileName.delete();
            FinalFileName.renameTo(NewFileName);


            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(NewFileName);
                XmlToJson xmlToJson = new XmlToJson.Builder(fileInputStream, null).build();


                NetworkActivity.getValue(xmlToJson.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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


