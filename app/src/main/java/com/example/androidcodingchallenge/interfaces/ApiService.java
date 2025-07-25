package com.example.androidcodingchallenge.interfaces;

import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("verify-user-email")
    Call<VerifyEmailResponse> verifyUserEmail(@Body EmailRequest request);

}
