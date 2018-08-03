package com.appentus.realestate.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetailPOJO implements Serializable{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_bio")
    @Expose
    private String userBio;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_otp")
    @Expose
    private String userOtp;
    @SerializedName("user_country_code")
    @Expose
    private String userCountryCode;
    @SerializedName("user_country")
    @Expose
    private String userCountry;
    @SerializedName("user_social_id")
    @Expose
    private String userSocialId;
    @SerializedName("user_device_token")
    @Expose
    private String userDeviceToken;
    @SerializedName("user_device_type")
    @Expose
    private String userDeviceType;
    @SerializedName("user_profile")
    @Expose
    private String userProfile;
    @SerializedName("user_status")
    @Expose
    private String userStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserSocialId() {
        return userSocialId;
    }

    public void setUserSocialId(String userSocialId) {
        this.userSocialId = userSocialId;
    }

    public String getUserDeviceToken() {
        return userDeviceToken;
    }

    public void setUserDeviceToken(String userDeviceToken) {
        this.userDeviceToken = userDeviceToken;
    }

    public String getUserDeviceType() {
        return userDeviceType;
    }

    public void setUserDeviceType(String userDeviceType) {
        this.userDeviceType = userDeviceType;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
