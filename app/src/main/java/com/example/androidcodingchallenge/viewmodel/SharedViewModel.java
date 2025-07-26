package com.example.androidcodingchallenge.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.OtpRequest;
import com.example.androidcodingchallenge.data.RegistrationRequest;
import com.example.androidcodingchallenge.data.RegistrationResponse;
import com.example.androidcodingchallenge.data.SendOtpResponse;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import com.example.androidcodingchallenge.data.VerifyOtpErrorResponse;
import com.example.androidcodingchallenge.data.VerifyOtpSuccessResponse;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.google.gson.Gson;
import java.util.Arrays;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedViewModel extends ViewModel {
    public static final String TAG = "SharedViewModel";
    private final UserRepository repository;

    public SharedViewModel(UserRepository repository) {
        this.repository = repository;
    }
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> emailVerifyStatus = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> sendOtpStatus = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> verifyOtpStatus = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> userRegisteredStatus = new MutableLiveData<>(false);

    // Event data for Fragments
    private final MutableLiveData<String> eventData = new MutableLiveData<>();

    // User data
    private final MutableLiveData<String> fName = new MutableLiveData<>();
    private final MutableLiveData<String> lName = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> birthday = new MutableLiveData<>();
    private final MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    private final MutableLiveData<String> company = new MutableLiveData<>();
    private final MutableLiveData<String> userToken = new MutableLiveData<>();

    // Verify email API call
    public void verifyEmailApi(String email) {
        EmailRequest request = new EmailRequest();
        request.setEmail(email);

        startLoading();
        repository.verifyEmail(request).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<VerifyEmailResponse> call, @NonNull Response<VerifyEmailResponse> response) {
                stopLoading();
                if (response.isSuccessful() && response.body() != null) {
                     Log.d(TAG, response.body().toString());
                     VerifyEmailResponse verifyResponse = response.body();

                     if(verifyResponse.getData().getIsVerified()) {
                         setFirstName(verifyResponse.getData().getFname());
                         setLastName(verifyResponse.getData().getLname());
                         setCompany(verifyResponse.getData().getCompanyName());
                         setMessage("Email verified successfully.");
                         setEmailVerifyStatus(true);
                     } else {
                         setMessage("Email not verified yet.");
                         setEmailVerifyStatus(false);
                     }
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        VerifyEmailResponse error = gson.fromJson(errorJson, VerifyEmailResponse.class);
                        Log.d(TAG, error.getData().getMessage());
                        setMessage(error.getData().getMessage());
                        setEmailVerifyStatus(false);
                   } catch (Exception e) {
                        setMessage(e.getMessage());
                        setEmailVerifyStatus(false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<VerifyEmailResponse> call, @NonNull Throwable t) {
                stopLoading();
                Log.d(TAG, t.getMessage());
                setMessage("Error: " + t.getMessage());
                setEmailVerifyStatus(false);
            }
        });
    }

    // Send Otp API call
    public void sendOTPApi(String email) {
        EmailRequest request = new EmailRequest();
        request.setEmail(email);

        startLoading();
        repository.sendOtp(request).enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResponse> call, @NonNull Response<SendOtpResponse> response) {
                stopLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, response.body().toString());
                    SendOtpResponse sendOtpResponse = response.body();
                    setToken(sendOtpResponse.getData().getToken());
                    setMessage(sendOtpResponse.getData().getMessage());
                    setSendOtpStatus(true);
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        SendOtpResponse error = gson.fromJson(errorJson, SendOtpResponse.class);
                        Log.d(TAG, error.getData().getMessage());
                        setMessage(error.getData().getMessage());
                        setSendOtpStatus(false);
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        setSendOtpStatus(false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResponse> call, @NonNull Throwable t) {
                stopLoading();
                Log.d(TAG, t.getMessage());
                setMessage("Error: " + t.getMessage());
                setSendOtpStatus(false);
            }
        });
    }

    // Verify Otp API call
    public void verifyOTPApi(String token, Integer otp) {
        OtpRequest request = new OtpRequest();
        request.setToken(token);
        request.setOtp(otp);

        startLoading();
        repository.verifyOtp(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                stopLoading();
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Gson gson = new Gson();
                        VerifyOtpSuccessResponse successResponse = gson.fromJson(responseBody, VerifyOtpSuccessResponse.class);
                        Log.d(TAG, successResponse.getData().get(0));
                        setMessage(successResponse.getData().get(0));
                        setVerifyOtpStatus(true);
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        setVerifyOtpStatus(false);
                    }
                } else if (response.code() == 400) {
                    try {
                        String errorBody = response.errorBody().string();
                        Gson gson = new Gson();
                        VerifyOtpErrorResponse errorResponse = gson.fromJson(errorBody, VerifyOtpErrorResponse.class);
                        Log.e(TAG, errorResponse.getData().getMessage());
                        setMessage(errorResponse.getData().getMessage());
                        setVerifyOtpStatus(false);
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        setVerifyOtpStatus(false);
                    }
                } else {
                    Log.e(TAG, "Unexpected error: " + response.code());
                    setMessage("Unexpected error: " + response.code());
                    setVerifyOtpStatus(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopLoading();
                Log.d(TAG, t.getMessage());
                setMessage("Error: " + t.getMessage());
                setVerifyOtpStatus(false);
            }
        });

    }

    // User registration API call
    public void userRegistrationApi(String fname, String lname, String email,
                                    String password, String birthday,
                                    String token, Long phoneNumber) {
        RegistrationRequest request = new RegistrationRequest();
        request.setFname(fname);
        request.setLname(lname);
        request.setEmail(email);
        request.setPassword(password);
        request.setBirthday(birthday);
        request.setToken(token);
        request.setPhoneNumber(phoneNumber);
        request.setAcceptedPrivacyPolicy(true);
        request.setLanguageId(1);
        request.setUserType(0);
        request.setTimeZone("America/New_York");
        request.setAreasOfInterest(Arrays.asList(1, 2, 3));
        request.setWellbeingPillars(Arrays.asList(1, 3, 5));

        startLoading();
        repository.registerUser(request).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
                stopLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, response.body().toString());
                    setMessage("User registered successfully.");
                    setUserRegisteredStatus(true);
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        RegistrationResponse error = gson.fromJson(errorJson, RegistrationResponse.class);
                        Log.d(TAG, error.getData().getMessage());
                        setMessage(error.getData().getMessage());
                        setUserRegisteredStatus(false);
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        setUserRegisteredStatus(false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponse> call, @NonNull Throwable t) {
                stopLoading();
                Log.d(TAG, t.getMessage());
                setMessage("Error: " + t.getMessage());
                setUserRegisteredStatus(false);
            }
        });
    }

    public LiveData<String> getMessage() { return message; }
    public void setMessage(String value) { message.setValue(value); }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public void startLoading() { isLoading.setValue(true); }
    public void stopLoading() { isLoading.setValue(false); }

    public LiveData<Boolean> getEmailVerifyStatus() { return emailVerifyStatus; }
    public void setEmailVerifyStatus(Boolean value) { emailVerifyStatus.setValue(value); }

    public LiveData<Boolean> getSendOtpStatus() { return sendOtpStatus; }
    public void setSendOtpStatus(Boolean value) { sendOtpStatus.setValue(value); }

    public LiveData<Boolean> getVerifyOtpStatus() { return verifyOtpStatus; }

    public void setVerifyOtpStatus(Boolean value) { verifyOtpStatus.setValue(value); }

    public LiveData<Boolean> getUserRegisteredStatus() { return userRegisteredStatus; }

    public void setUserRegisteredStatus(Boolean value) { userRegisteredStatus.setValue(value); }

    public LiveData<String> getEventData() { return eventData; }
    public void sendEvent(String message) { eventData.setValue(message); }

    public LiveData<String> getFirstName() { return fName; }
    public void setFirstName(String value) { fName.setValue(value); }

    public LiveData<String> getLastName() { return lName; }
    public void setLastName(String value) { lName.setValue(value); }

    public LiveData<String> getEmail() { return email; }
    public void setEmail(String value) { email.setValue(value); }

    public LiveData<String> getBirthday() { return birthday; }
    public void setBirthday(String value) { birthday.setValue(value); }

    public LiveData<String> getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String value) { phoneNumber.setValue(value); }

    public LiveData<String> getPassword() { return password; }
    public void setPassword(String value) { password.setValue(value); }

    public LiveData<String> getCompany() { return company; }
    public void setCompany(String value) { company.setValue(value); }

    public LiveData<String> getToken() { return userToken; }
    public void setToken(String value) { userToken.setValue(value); }

}
