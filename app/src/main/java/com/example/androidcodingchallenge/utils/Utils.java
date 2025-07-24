package com.example.androidcodingchallenge.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Utils {
    public static boolean isInvalidEmail(String email) {
        return TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
