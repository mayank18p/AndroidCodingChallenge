package com.example.androidcodingchallenge.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequest {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
