package com.example.androidcodingchallenge.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.androidcodingchallenge.data.EmailRequest;
import com.example.androidcodingchallenge.data.VerifyEmailResponse;
import com.example.androidcodingchallenge.repository.UserRepository;
import retrofit2.Call;

public class SharedViewModel extends ViewModel {
    public static final String TAG = "SharedViewModel";
    private final UserRepository repository;
    public SharedViewModel(UserRepository repository) {
        this.repository = repository;
    }
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final MutableLiveData<Boolean> emailVerifyStatus = new MutableLiveData<>();

    private final MutableLiveData<Boolean> sendOtpStatus = new MutableLiveData<>();

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


    public void verifyEmailApi(String email) {
        startLoading();
        EmailRequest request = new EmailRequest();
        request.setEmail(email);
        repository.verifyEmail(request).enqueue(new retrofit2.Callback<>() {
            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
                stopLoading();
                Log.d(TAG, t.getMessage());
                setMessage("Error: " + t.getMessage());
                sendEmailVerifyStatus(false);
            }

            @Override
            public void onResponse(retrofit2.Call<VerifyEmailResponse> call, retrofit2.Response<VerifyEmailResponse> response) {
                stopLoading();
                if (response.isSuccessful() && response.body() != null) {
                     Log.d(TAG, response.body().toString());
                     VerifyEmailResponse verifyResponse = response.body();
                     setFirstName(verifyResponse.getData().getFname());
                     setLastName(verifyResponse.getData().getLname());
                     setCompany(verifyResponse.getData().getCompanyName());
                     sendEmailVerifyStatus(true);
                     setMessage("Email verified successfully: " + response.body());
                } else {
                    assert response.body() != null;
                    Log.d(TAG, response.body().getError());
                    setMessage("Failed to verify: " + response.body().getError());
                    sendEmailVerifyStatus(false);
                }
            }
        });
    }

    public LiveData<String> getMessage() { return message; }
    public void setMessage(String value) { message.setValue(value); }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public void startLoading() { isLoading.setValue(true); }
    public void stopLoading() { isLoading.setValue(false); }

    public LiveData<Boolean> getEmailVerifyStatus() { return emailVerifyStatus; }
    public void sendEmailVerifyStatus(Boolean value) { emailVerifyStatus.setValue(value); }

    public LiveData<Boolean> getSendOtpStatus() { return sendOtpStatus; }
    public void sendSendOtpStatus(Boolean value) { sendOtpStatus.setValue(value); }

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
