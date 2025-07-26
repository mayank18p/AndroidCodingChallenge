package com.example.androidcodingchallenge.repository;

import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.OtpRequest;
import com.example.androidcodingchallenge.data.RegistrationRequest;
import com.example.androidcodingchallenge.data.RegistrationResponse;
import com.example.androidcodingchallenge.data.SendOtpResponse;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import com.example.androidcodingchallenge.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

public class UserRepository {
    public Call<VerifyEmailResponse> verifyEmail(@Body EmailRequest request) {
        return RetrofitInstance.apiService.verifyUserEmail(request);
    }
    public Call<SendOtpResponse> sendOtp(@Body EmailRequest request) {
        return RetrofitInstance.apiService.sendEmailOtp(request);
    }

    public Call<ResponseBody> verifyOtp(@Body OtpRequest request) {
        return RetrofitInstance.apiService.verifyEmailOtp(request);
    }

    public Call<RegistrationResponse> registerUser(@Body RegistrationRequest request) {
        return RetrofitInstance.apiService.registerUser(request);
    }
}
