package com.example.androidcodingchallenge.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showShortToast(Context context, String message) {
        if (context != null && message != null && !message.trim().isEmpty()) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToast(Context context, String message) {
        if (context != null && message != null && !message.trim().isEmpty()) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
