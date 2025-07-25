package com.example.androidcodingchallenge.repository;

import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import com.example.androidcodingchallenge.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.http.Body;

public class UserRepository {
    public Call<VerifyEmailResponse> verifyEmail(@Body EmailRequest request) {
        return RetrofitInstance.apiService.verifyUserEmail(request);
    }
}
