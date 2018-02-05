package com.example.terrence.tianyi4_subbook;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Terrence on 2018/1/29.
 */

public class Subscription {
    public String name;
    public Date date;
    public double charge;
    public String comment;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Subscription(){

        date = new Date();

    }

    public String getName() {
        return name;
    }

    public void setName(String name)  {

            this.name = name;

    }
    // taken from https://stackoverflow.com/questions/8573250/android-how-can-i-convert-string-to-date
    // 2018-2-3

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        try{
            this.date = format.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment){ this.comment = comment; }

    @Override
    public String toString() {
        return name;
    }
}
