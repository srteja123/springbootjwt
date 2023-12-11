package com.example.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

import java.util.concurrent.TimeUnit;

public class TimeStamp {
    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String fetch_present_string_time() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestampFormat.format(timestamp);
    }

    public static Date fetch_present_time() {
        return new java.util.Date();
    }

    public static String fetch_future_string_time(int bufferTime) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(bufferTime));
        return timestampFormat.format(timestamp);
    }

    public static Date fetch_future_time(int bufferTime) {
        return new java.util.Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(bufferTime));
    }

    public static String fetch_present_timestamp() {
        Long current_time = System.currentTimeMillis();
        String current_time_string = String.valueOf(current_time);  
        return current_time_string;
    }

    public static String fetch_future_timestamp(int bufferTime) {
        Long future_time = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(bufferTime);
        String future_time_string = String.valueOf(future_time);  
        return future_time_string;
    }
}

