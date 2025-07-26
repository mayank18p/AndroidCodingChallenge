package com.example.androidcodingchallenge.interfaces;

import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.OtpRequest;
import com.example.androidcodingchallenge.data.RegistrationRequest;
import com.example.androidcodingchallenge.data.RegistrationResponse;
import com.example.androidcodingchallenge.data.SendOtpResponse;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("verify-user-email")
    Call<VerifyEmailResponse> verifyUserEmail(@Body EmailRequest request);

    @POST("send-otp-for-user-registration")
    Call<SendOtpResponse> sendEmailOtp(@Body EmailRequest request);

    @POST("verify-otp-for-user-registration")
    Call<ResponseBody> verifyEmailOtp(@Body OtpRequest request);

    @POST("user-registration")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest request);

}
