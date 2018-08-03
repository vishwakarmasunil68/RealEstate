package com.appentus.realestate.pojo.location;

import android.text.Html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerLocationPOJO implements Serializable{

    @SerializedName("LocationId")
    @Expose
    private String locationId;
    @SerializedName("PlaceId")
    @Expose
    private String placeId;
    @SerializedName("LocationLattitude")
    @Expose
    private String locationLattitude;
    @SerializedName("LocationLongitude")
    @Expose
    private String locationLongitude;
    @SerializedName("LocationUrl")
    @Expose
    private String locationUrl;
    @SerializedName("LocationAddress")
    @Expose
    private String locationAddress;
    @SerializedName("LocationVicinity")
    @Expose
    private String locationVicinity;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLocationLattitude() {
        return locationLattitude;
    }

    public void setLocationLattitude(String locationLattitude) {
        this.locationLattitude = locationLattitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getLocationAddress() {
        if(locationAddress!=null){
            return Html.fromHtml(locationAddress).toString();
        }
        return "";
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationVicinity() {
        return locationVicinity;
    }

    public void setLocationVicinity(String locationVicinity) {
        this.locationVicinity = locationVicinity;
    }
}
