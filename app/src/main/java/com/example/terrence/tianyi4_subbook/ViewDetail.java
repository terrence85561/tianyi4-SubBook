package com.example.terrence.tianyi4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static com.example.terrence.tianyi4_subbook.MainActivity.saveInFile;


public class ViewDetail extends AppCompatActivity {

    private Subscription subscription;
    private TextView displayName,displayDate,displayCharge,displayComments;
    private int itemPosition;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);


        displayName = (TextView) findViewById(R.id.displayNameTextView);
        displayDate = (TextView) findViewById(R.id.displayDateTextView);
        displayCharge = (TextView) findViewById(R.id.displayChargeTextView);
        displayComments = (TextView) findViewById(R.id.displayCommentTextView);
        //get corresponding data
        Intent intent = getIntent();
        itemPosition = intent.getIntExtra("itemPos", 1);
        subscription = InfoData.getInstance().getSubscriptionsArrayList().get(itemPosition);

        displayName.setText(subscription.getName());

        Date date_toString = subscription.getDate();
        String dateStr = format.format(date_toString);
        displayDate.setText(dateStr);
        displayCharge.setText(Double.toString(subscription.getCharge()));
        displayComments.setText(subscription.getComment());


    }
    //go back to AddSubscription activity to edit
    //pass the data's position
    public void onEditSubscription(View view) {
        Intent editIntent = new Intent(ViewDetail.this,AddSubscription.class);
        editIntent.putExtra("addInfo","edit");
        editIntent.putExtra("itemPos",itemPosition);
        startActivityForResult(editIntent,7);
    }

    public void onDeleteSubscription(View view) {
        InfoData.getInstance().getSubscriptionsArrayList().remove(itemPosition);
        InfoData.saveInFile(this);
        finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==7 && resultCode==RESULT_OK){
            finish();
        }
    }
}
