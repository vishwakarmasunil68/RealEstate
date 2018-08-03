package com.appentus.realestate.pojo;

import com.appentus.realestate.pojo.location.LocationPOJO;
import com.appentus.realestate.pojo.location.NewLocationPOJO;

import java.io.Serializable;
import java.util.List;

public class AddPropertyPOJO implements Serializable{

    String property_offer;
    String property_category;
    List<String> addPropertyAttachmentsPOJOS;
    LocationPOJO locationPOJO;
    String room;
    String toilet;
    String halls;
    String age;
    String amenities;
    String price;
    String price_id;
    String size;
    String size_id;
    String extra_notes;

    public String getProperty_offer() {
        return property_offer;
    }

    public void setProperty_offer(String property_offer) {
        this.property_offer = property_offer;
    }

    public String getProperty_category() {
        return property_category;
    }

    public void setProperty_category(String property_category) {
        this.property_category = property_category;
    }

    public List<String> getAddPropertyAttachmentsPOJOS() {
        return addPropertyAttachmentsPOJOS;
    }

    public void setAddPropertyAttachmentsPOJOS(List<String> addPropertyAttachmentsPOJOS) {
        this.addPropertyAttachmentsPOJOS = addPropertyAttachmentsPOJOS;
    }

    public LocationPOJO getLocationPOJO() {
        return locationPOJO;
    }

    public void setLocationPOJO(LocationPOJO locationPOJO) {
        this.locationPOJO = locationPOJO;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getHalls() {
        return halls;
    }

    public void setHalls(String halls) {
        this.halls = halls;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getExtra_notes() {
        return extra_notes;
    }

    public void setExtra_notes(String extra_notes) {
        this.extra_notes = extra_notes;
    }
}
