package com.appentus.realestate.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PropertyPOJO implements Serializable {
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("property_price")
    @Expose
    private String propertyPrice;
    @SerializedName("property_price_id")
    @Expose
    private String propertyPriceId;
    @SerializedName("property_size_id")
    @Expose
    private String propertySizeId;
    @SerializedName("property_size")
    @Expose
    private String propertySize;
    @SerializedName("property_location")
    @Expose
    private String propertyLocation;
    @SerializedName("property_lat")
    @Expose
    private String propertyLat;
    @SerializedName("property_long")
    @Expose
    private String propertyLong;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("property_purpose")
    @Expose
    private String propertyPurpose;
    @SerializedName("rent_period")
    @Expose
    private String rentPeriod;
    @SerializedName("room_count")
    @Expose
    private String roomCount;
    @SerializedName("toilet_count")
    @Expose
    private String toiletCount;
    @SerializedName("additional_note")
    @Expose
    private String additionalNote;
    @SerializedName("shop_count")
    @Expose
    private String shopCount;
    @SerializedName("hall_count")
    @Expose
    private String hallCount;
    @SerializedName("floor_number")
    @Expose
    private String floorNumber;
    @SerializedName("property_age")
    @Expose
    private String propertyAge;
    @SerializedName("amenities")
    @Expose
    private String amenities;
    @SerializedName("insert_date")
    @Expose
    private String insertDate;
    @SerializedName("property_status")
    @Expose
    private String propertyStatus;
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
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_arabic")
    @Expose
    private String categoryArabic;
    @SerializedName("offer_name")
    @Expose
    private String offerName;
    @SerializedName("offer_arabic")
    @Expose
    private String offerArabic;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("attchment_file_path")
    @Expose
    private String attchment_file_path;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyPriceId() {
        return propertyPriceId;
    }

    public void setPropertyPriceId(String propertyPriceId) {
        this.propertyPriceId = propertyPriceId;
    }

    public String getPropertySizeId() {
        return propertySizeId;
    }

    public void setPropertySizeId(String propertySizeId) {
        this.propertySizeId = propertySizeId;
    }

    public String getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(String propertySize) {
        this.propertySize = propertySize;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getPropertyLat() {
        return propertyLat;
    }

    public void setPropertyLat(String propertyLat) {
        this.propertyLat = propertyLat;
    }

    public String getPropertyLong() {
        return propertyLong;
    }

    public void setPropertyLong(String propertyLong) {
        this.propertyLong = propertyLong;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(String rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(String toiletCount) {
        this.toiletCount = toiletCount;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount;
    }

    public String getHallCount() {
        return hallCount;
    }

    public void setHallCount(String hallCount) {
        this.hallCount = hallCount;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getPropertyAge() {
        return propertyAge;
    }

    public void setPropertyAge(String propertyAge) {
        this.propertyAge = propertyAge;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryArabic() {
        return categoryArabic;
    }

    public void setCategoryArabic(String categoryArabic) {
        this.categoryArabic = categoryArabic;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferArabic() {
        return offerArabic;
    }

    public void setOfferArabic(String offerArabic) {
        this.offerArabic = offerArabic;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAttchment_file_path() {
        return attchment_file_path;
    }

    public void setAttchment_file_path(String attchment_file_path) {
        this.attchment_file_path = attchment_file_path;
    }

    @Override
    public String toString() {
        return "PropertyPOJO{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
