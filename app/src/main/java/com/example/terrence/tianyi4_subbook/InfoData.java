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

public class InfoData {
    private static  InfoData ourInstance = new InfoData();

    private ArrayList<Subscription> subscriptionsArrayList;

    public static InfoData getInstance() {
        return ourInstance;
    }

    public InfoData() { subscriptionsArrayList = new ArrayList<Subscription>();
    }

    public ArrayList<Subscription>getSubscriptionsArrayList(){return subscriptionsArrayList;}




    public static void loadFromFile(Context context) {

        try {
            FileInputStream fis = context.openFileInput("file.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //gson convert from json into arrayList
            // Tanken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25
            //Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            ourInstance = gson.fromJson(in,InfoData.class);

        } catch (FileNotFoundException e) {
            ourInstance = new InfoData();
        }

    }

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
