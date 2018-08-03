package com.appentus.realestate.pojo.location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeometryPOJO implements Serializable {
    @SerializedName("location")
    private LatLongPOJO location;
    @SerializedName("viewport")
    private ViewPortPOJO viewport;

    public LatLongPOJO getLocation() {
        return location;
    }

    public void setLocation(LatLongPOJO location) {
        this.location = location;
    }

    public ViewPortPOJO getViewport() {
        return viewport;
    }

    public void setViewport(ViewPortPOJO viewport) {
        this.viewport = viewport;
    }
}
