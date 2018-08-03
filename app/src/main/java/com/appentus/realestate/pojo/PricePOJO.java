package com.appentus.realestate.pojo;

import java.io.Serializable;

public class PricePOJO implements Serializable {
    String property_price_id;
    String price;
    String status;


    public String getProperty_price_id() {
        return property_price_id;
    }

    public void setProperty_price_id(String property_price_id) {
        this.property_price_id = property_price_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
