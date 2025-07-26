package com.example.androidcodingchallenge.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.example.androidcodingchallenge.R;

public class TimerUtils {
    public static CountDownTimer countDownTimer;
    private static String formattedString;

    public static void startOtpTimer(TextView textView, Context context) {
        long timeInMillis = 3 * 60 * 1000;
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                formattedString = String.format(context.getString(R.string.resend_otp_in_), minutes, seconds);
                textView.setText(formattedString);
                textView.setEnabled(false);
                textView.setTextColor(context.getResources().getColor(R.color.text_field_color));
            }

            @Override
            public void onFinish() {
                formattedString = context.getString(R.string.resend);
                textView.setText(formattedString);
                textView.setEnabled(true);
                textView.setTextColor(context.getResources().getColor(R.color.error_color));
            }
        }.start();
    }
}
