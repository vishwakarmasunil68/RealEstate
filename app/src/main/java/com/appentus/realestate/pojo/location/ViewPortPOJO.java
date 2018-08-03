package com.appentus.realestate.pojo.location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewPortPOJO implements Serializable {
    @SerializedName("northeast")
    LocationPOJO northeastLocation;
    @SerializedName("southwest")
    LocationPOJO southwestLocation;

    public LocationPOJO getNortheastLocation() {
        return northeastLocation;
    }

    public void setNortheastLocation(LocationPOJO northeastLocation) {
        this.northeastLocation = northeastLocation;
    }

    public LocationPOJO getSouthwestLocation() {
        return southwestLocation;
    }

    public void setSouthwestLocation(LocationPOJO southwestLocation) {
        this.southwestLocation = southwestLocation;
    }
}
