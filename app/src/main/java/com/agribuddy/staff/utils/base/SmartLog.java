package com.agribuddy.staff.utils.base;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 6/18/15.
 */
public class SmartLog {

    public static final String TAG = "LOG";

    public static void show(String message) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d(message);
    }

    public static void show(byte[] responseBody) {
        if (responseBody != null) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            try {
                JSONObject jsonObject = new JSONObject(new String(responseBody));
                Logger.d(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void d(String message) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d(message);
    }

    public static void e(String message) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.e(message);
    }

    public static void show(JSONObject message) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.json(message.toString());
    }
}
