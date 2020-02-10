package com.agribuddy.staff.utils.base;

import android.content.Context;
import android.widget.Toast;

import com.agribuddy.staff.R;

public class SmartToast {

    public static void showToast(Context mContext, String message) {
        if (mContext != null) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showNoIntenet(Context mContext) {
        if (mContext != null) {
            Toast.makeText(
                    mContext,
                    mContext.getResources().getString(
                            R.string.dialog_message_disconnect), Toast.LENGTH_LONG)
                    .show();

        }
    }

    public static void showEmptyField(Context mContext) {
        if (mContext != null) {
            Toast.makeText(
                    mContext,
                    mContext.getResources().getString(
                            R.string.dialog_message_empty_field), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public static void showDeniedPermission(Context mContext) {
        if (mContext != null) {
            Toast.makeText(
                    mContext,
                    mContext.getResources().getString(
                            R.string.message_dont_have_access_permission), Toast.LENGTH_LONG)
                    .show();
        }
    }

}
