package com.project.agroworld.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.project.agroworld.ui.DashboardActivity;
import com.project.agroworld.ui.UserProfileActivity;

import java.util.Locale;

public class Constants {

    public static final String BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    public static final String BASE_URL_SHEET_DB = "https://sheetdb.io/api/v1/";
    public static String API_KEY = "92f4e9a9c233be99f0b33d1c58c72386";
    public static String NEWS_WEB_URL = "https://krishijagran.com/feeds/?utm_source=homepage&utm_medium=browse&utm_campaign=home_browse&utm_id=homepage_browse";

    public static int GOOGLE_PAY_REQ_CODE = 123;
    public static String NAME = "Agro World";
    public static String UPI_ID = "bhavesh.patil0325-3@okaxis";
    public static String TRANSACTION_NOTE = "Seeds Shopping";
    public static int REQUEST_CODE = 99;
    public static int GPS_REQUEST_CODE = 999;
    public static String ENGLISH_KEY = "EnglishLang";
    public static String HINDI_KEY = "HindiLang";


    public static void printLog(String message) {
        Log.d("AgroWorldUser", message);
    }

    public static void identifyUser(FirebaseUser user, Context context) {
        if (user != null) {
            if (user.getEmail().equals("devdeveloper66@gmail.com")) {
                //Manufacture user
                Intent manufacturerIntent = new Intent(context, UserProfileActivity.class);
                manufacturerIntent.putExtra("manufacturerUser", "manufacturer");
                context.startActivity(manufacturerIntent);
            } else if (user.getEmail().equals("nap.napster08@gmail.com")) {
                //transport user
                Intent transportIntent = new Intent(context, UserProfileActivity.class);
                transportIntent.putExtra("transportUser", "transport");
                context.startActivity(transportIntent);
            } else {
                //Farmer
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
            }
        }
    }

    public static void setAppLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        context.createConfigurationContext(config);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void bindImage(View view, String url, ImageView imageView) {
        Glide.with(view).load(url).into(imageView);
    }

    public static boolean contactValidation(String contact) {
        return contact.length() == 10;
    }

}
