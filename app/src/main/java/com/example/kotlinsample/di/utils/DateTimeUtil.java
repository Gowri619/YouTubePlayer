package com.example.kotlinsample.di.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.kotlinsample.di.exception.RetrofitException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.internal.Utils;
import io.reactivex.exceptions.CompositeException;

public class DateTimeUtil {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String PRETTY_PATTERN = "dd.MM.yyyy HH:mm";
    private static final String TITLE_PATTERN = "dd MMM yyyy";
    private static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd";

    public static String getRequestDateFormatted(DateTime date) {
        return date.toString(REQUEST_DATE_FORMAT);
    }

    public static String toString(DateTime dateTime) {
        return dateTime.toString(PATTERN);
    }

    public static String toRequestString(DateTime dateTime) {
        return dateTime.toString(REQUEST_DATE_FORMAT);
    }

    public static DateTime toDateTime(String dateTime) {
        return DateTime.parse(dateTime, DateTimeFormat.forPattern(PATTERN));
    }

    public static String toPrettyString(DateTime time) {
        return time.toString(PRETTY_PATTERN);
    }

    public static String toTitleString(DateTime dateTime) {
        return dateTime.toString(TITLE_PATTERN);
    }

    public static DateTime toDateTimeFromMillis(long millis) {
        return new DateTime(millis);
    }

    public static String toRequestDate(String date) {
        try {
            final String OLD_FORMAT = "MM-dd-yyyy";
            final String NEW_FORMAT = "yyyy-MM-dd";

            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            Date d = sdf.parse(date);
            sdf.applyPattern(NEW_FORMAT);
            date = sdf.format(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getErrorMessage(Throwable throwable, Context context) {
        if (throwable instanceof RetrofitException) {
            return String.valueOf(throwable);
        } else if (throwable instanceof CompositeException) {
            return ((CompositeException) throwable).getExceptions().get(0).getMessage();
        } else if (throwable instanceof IllegalStateException) {
            return null;
        } else {
            String message = throwable.getMessage();
            if (TextUtils.isEmpty(message)) {
                return throwable.getClass().getName();
            } else {
                return message;
            }
        }
    }

}
