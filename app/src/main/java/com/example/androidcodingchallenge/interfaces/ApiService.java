package com.example.androidcodingchallenge.interfaces;

import com.example.androidcodingchallenge.data.VerifyEmailRequest;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("verify-user-email")
    Call<VerifyEmailResponse> verifyUserEmail(@Body VerifyEmailRequest request);


}
