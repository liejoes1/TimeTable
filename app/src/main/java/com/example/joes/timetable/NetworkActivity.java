package com.example.joes.timetable;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.IOException;


/**
 * Created by Joes on 13/3/2018.
 */

public class NetworkActivity {
    private static final String TIMETABLE_URL = "https://lms.apiit.edu.my/intake-timetable/download_timetable/timetableXML.zip";
    private static String DIRECTORY_PATH;
    private static Context appContext;
    private static Context mainContext;

    public static void setContext(Context mappContext, Context mmainContext) {
        appContext = mappContext;
        mainContext = mmainContext;
    }

    public static void startDownload() {
        DIRECTORY_PATH = Utils.getRootDirPath(appContext);


        downloadTimeTable();
    }


    public static void downloadTimeTable() {

        PRDownloader.download(TIMETABLE_URL, DIRECTORY_PATH, "TimeTable.zip")

                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        String zipFile = DIRECTORY_PATH + "/TimeTable.zip";
                        String unzipLocation = DIRECTORY_PATH + "/Timetable/";

                        Decompress decompress = new Decompress(zipFile, unzipLocation);
                        decompress.unzip();

                        try {
                            ParseXML.ReadFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });

    }
}


