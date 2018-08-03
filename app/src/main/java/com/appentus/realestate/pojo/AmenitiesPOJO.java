package com.appentus.realestate.pojo;

import java.io.Serializable;

public class AmenitiesPOJO implements Serializable{
    String amenities_id;
    String amenities_name;
    String status;

    public String getAmenities_id() {
        return amenities_id;
    }

    public void setAmenities_id(String amenities_id) {
        this.amenities_id = amenities_id;
    }

    public String getAmenities_name() {
        return amenities_name;
    }

    public void setAmenities_name(String amenities_name) {
        this.amenities_name = amenities_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
