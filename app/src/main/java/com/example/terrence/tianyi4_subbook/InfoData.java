package com.example.terrence.tianyi4_subbook;

import android.content.Context;
import android.icu.text.IDNA;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by Terrence on 2018/2/3.
 */

/**
 * This is a singleton used to store subscription data
 * set Subscription arraylist
 * taken from https://stackoverflow.com/questions/4878159/whats-the-best-way-to-share-data-between-activities
 * 2018-02-03
 */
public class InfoData {

    private ArrayList<Subscription> subscriptionsArrayList;
    public ArrayList<Subscription>getSubscriptionsArrayList(){return subscriptionsArrayList;}
    private static  InfoData ourInstance = new InfoData();
    public static InfoData getInstance() {
        return ourInstance;
    }
    private InfoData() { subscriptionsArrayList = new ArrayList<Subscription>();
    }

    /**
     *
     * @param context
     * use to load the data
     * use gson to pass data
     */

    public static void loadFromFile(Context context) {

        try {
            FileInputStream fis = context.openFileInput("file.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //gson convert from json into arrayList
            // Tanken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25

            ourInstance = gson.fromJson(in,InfoData.class);
        } catch (FileNotFoundException e) {
            ourInstance = new InfoData();
        }
    }
    /**
     *
     * @param context
     * use to save data
     * use gson to pass data
     */

    public static void saveInFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("file.sav",
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(ourInstance,out);
            out.flush();


        }  catch (IOException e) {
            throw new RuntimeException();
        }
    }






}
