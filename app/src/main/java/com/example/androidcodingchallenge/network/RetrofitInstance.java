package com.example.androidcodingchallenge.network;

import androidx.annotation.NonNull;
import com.example.androidcodingchallenge.interfaces.ApiService;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://staging.api.gcp.woliba.io/v1/";

    private static Retrofit retrofit;

    private static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }

    public static final ApiService apiService = getInstance().create(ApiService.class);

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = getHttpClientBuilder();

        builder.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Accept", "application/json")
                        .header("AUTHTOKEN", "$2y$10$rGDpMbQDv.UuELqMA7Cgwu0FH.riovvNaR/WfI11exXS6L4YhFzAa")
                        .build();
                return chain.proceed(request);
            }
        });

        return builder.build();
    }

    private static OkHttpClient.Builder getHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }
}

