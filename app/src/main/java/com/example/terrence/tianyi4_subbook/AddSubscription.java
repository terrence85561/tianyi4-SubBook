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


/**
 * This activity handle edit mode and add mode
 * user can add new subscription or edit exist subscription through this activity
 */
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

        /**
         * set mode(add|edit)
         * "add" is the value sent by MainActivity when user click "new subscriptio" button
         * "edit" is the value sent by ViewDetail activity when user click "edit" button
         */
        if (submissionContent.getStringExtra("addInfo").equals("add")){
            mode = 1;
        }else if (submissionContent.getStringExtra("addInfo").equals("edit")){
            mode = 0;
        }
        //if is "add" mode, initial a new Subscription
        if (mode == 1){
            subscription = new Subscription();
        }else if (mode == 0){
            // if is "edit" mode, get the position value from ViewDetail and get the corresponding subscription
            int itemPosition = submissionContent.getIntExtra("itemPos",1);
            subscription = InfoData.getInstance().getSubscriptionsArrayList().get(itemPosition);

        }

        userNameEditText = (EditText)findViewById(R.id.userNameEditText);
        startedDateEditText = (EditText)findViewById(R.id.startedDateEditText);
        userChargeEditText = (EditText)findViewById(R.id.userChargeEditText);
        userCommentEditText = (EditText)findViewById(R.id.userCommentEditText);

        //initialize the content in EditText

        userNameEditText.setText(subscription.getName());

        // convert date to string

        Date date_toString = subscription.getDate();
        String dateStr = format.format(date_toString);

        startedDateEditText.setText(dateStr);

        userChargeEditText.setText(Double.toString(subscription.getCharge()));
        userCommentEditText.setText(subscription.getComment());

    }

    /**
     *
     * @param view
     * when user click "submit" button
     * check all inputs valid or not
     * present a prompt if the input is invalid
     * save the edited or new subscription
     */
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
        if (userChargeEditText.getText().toString().matches("")){
            Toast.makeText(this,"Please enter charge.",Toast.LENGTH_LONG).show();
            return;
        }
        // verify input date format valid
        // use regular expression
        if(!startedDateEditText.getText().toString().matches("\\d{4}-[01]\\d-[0-3]\\d")){
            Toast.makeText(this,"please enter in format yyyy-mm-dd",Toast.LENGTH_LONG).show();
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
