package com.example.terrence.tianyi4_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * This is the MainActivity that control the initial interface
 * load data first
 * show the listView of added subscription
 * can go to add new subcription from this activity
 */
public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Subscription> myAdapter;
    private ListView subscriptionListView;
    private TextView totalChargeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *  load data
         */
        InfoData.loadFromFile(this);
        setContentView(R.layout.activity_main);


        subscriptionListView = (ListView) findViewById(R.id.subscriptionListView);
        myAdapter = new ArrayAdapter<>(this,R.layout.list_item,InfoData.getInstance().getSubscriptionsArrayList());


        /**
         *  when user click one subscription in the list view
         *  go to ViewDetail activity to see the detail
         */

        subscriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(MainActivity.this,ViewDetail.class);

                // send the position value which indicates the index of the clicked subscription in adapterView
                intent.putExtra("itemPos",position);

                startActivity(intent);
            }
        });

    }

    /**
     *
     * @param view
     * when user click the "new subscription" button
     * go to AddSubscription activity as "add" mode to set up data
     */
    public void onAddNewSubscription(View view) {

        Intent getInfoIntent = new Intent(this,AddSubscription.class);

        getInfoIntent.putExtra("addInfo","add");

        startActivity(getInfoIntent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        /**
         * show all subscriptions
         */
        subscriptionListView.setAdapter(myAdapter);

        /**
         * set current total charge
         */
        totalChargeTextView = (TextView)findViewById(R.id.totalChargeTextView);

         // call sumCharge method to calculate current total charge
        double sumUP = sumCharge();
        totalChargeTextView.setText(Double.toString(sumUP));


    }

    /**
     *
     * @return total monthly charge
     */
    private double sumCharge(){
        double totalCharge = 0.0;
        double currentCharge;
        // get each subscription in ArrayList
        for (Subscription subscription : InfoData.getInstance().getSubscriptionsArrayList()){
            currentCharge = subscription.getCharge();
            totalCharge = totalCharge + currentCharge;
        }
        return totalCharge;
    }




}
