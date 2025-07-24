package com.example.androidcodingchallenge.data;

public class VerifyEmailResponse {
    private boolean status;
    private Data data;
    private String error; // nullable

    public boolean isStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static class Data {
        private boolean is_verified;

        public boolean isVerified() {
            return is_verified;
        }
    }
}
