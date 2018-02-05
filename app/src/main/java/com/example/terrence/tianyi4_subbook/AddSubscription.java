package com.example.terrence.tianyi4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static com.example.terrence.tianyi4_subbook.MainActivity.saveInFile;

public class AddSubscription extends AppCompatActivity {

    private EditText userNameEditText,startedDateEditText,userChargeEditText,userCommentEditText;
    private Subscription subscription;
    private int mode;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_subscription);
        Intent submissionContent = getIntent();
        //set mode(add|edit)
        if (submissionContent.getStringExtra("addInfo").equals("add")){
            mode = 1;
        }else if (submissionContent.getStringExtra("addInfo").equals("edit")){
            mode = 0;
        }

        if (mode == 1){
            subscription = new Subscription();
        }else if (mode == 0){
            int itemPosition = submissionContent.getIntExtra("itemPos",1);
            subscription = InfoData.getInstance().getSubscriptionsArrayList().get(itemPosition);

        }

        userNameEditText = (EditText)findViewById(R.id.userNameEditText);
        startedDateEditText = (EditText)findViewById(R.id.startedDateEditText);
        userChargeEditText = (EditText)findViewById(R.id.userChargeEditText);
        userCommentEditText = (EditText)findViewById(R.id.userCommentEditText);

        //initialize the content in EditText
        //DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        userNameEditText.setText(subscription.getName());

        // convert date to string

        Date date_toString = subscription.getDate();
        String dateStr = format.format(date_toString);

        startedDateEditText.setText(dateStr);

        userChargeEditText.setText(Double.toString(subscription.getCharge()));
        userCommentEditText.setText(subscription.getComment());





    }

    public void onSubmitSubscription(View view)  {
        // name, date, charge have to be filled
        if (userNameEditText.getText().toString().matches("")) {
            Toast.makeText(this, "Please enter name.", Toast.LENGTH_LONG).show();
            return;
        }
        if (startedDateEditText.getText().toString().matches("")){
            Toast.makeText(this,"Please enter date.",Toast.LENGTH_LONG).show();
            return;
        }
        if(!startedDateEditText.getText().toString().matches("\\d{4}-\\d{2}-\\d{2}")){
            Toast.makeText(this,"please enter in format yyyy-mm-dd",Toast.LENGTH_LONG).show();
            return;
        }
        if (userChargeEditText.getText().toString().matches("")){
            Toast.makeText(this,"Please enter charge.",Toast.LENGTH_LONG).show();
            return;
        }

        // set data into subscription object
        subscription.setName(userNameEditText.getText().toString());
        subscription.setDate(startedDateEditText.getText().toString());
        subscription.setCharge(Double.parseDouble(userChargeEditText.getText().toString()));
        subscription.setComment(userCommentEditText.getText().toString());

        if(mode == 1){
            InfoData.getInstance().getSubscriptionsArrayList().add(subscription);
        }

        setResult(RESULT_OK);
        InfoData.saveInFile(this);

        finish();
    }
}
