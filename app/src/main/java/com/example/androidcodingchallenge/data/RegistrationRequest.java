package com.example.androidcodingchallenge.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationRequest {
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("time_zone")
    @Expose
    private String timeZone;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("areas_of_interest")
    @Expose
    private List<Integer> areasOfInterest;
    @SerializedName("wellbeing_pillars")
    @Expose
    private List<Integer> wellbeingPillars;
    @SerializedName("accepted_privacy_policy")
    @Expose
    private Boolean acceptedPrivacyPolicy;
    @SerializedName("language_id")
    @Expose
    private Integer languageId;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("phone_number")
    @Expose
    private Long phoneNumber;
    @SerializedName("user_type")
    @Expose
    private Integer userType;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getAreasOfInterest() {
        return areasOfInterest;
    }

    public void setAreasOfInterest(List<Integer> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }

    public List<Integer> getWellbeingPillars() {
        return wellbeingPillars;
    }

    public void setWellbeingPillars(List<Integer> wellbeingPillars) {
        this.wellbeingPillars = wellbeingPillars;
    }

    public Boolean getAcceptedPrivacyPolicy() {
        return acceptedPrivacyPolicy;
    }

    public void setAcceptedPrivacyPolicy(Boolean acceptedPrivacyPolicy) {
        this.acceptedPrivacyPolicy = acceptedPrivacyPolicy;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
