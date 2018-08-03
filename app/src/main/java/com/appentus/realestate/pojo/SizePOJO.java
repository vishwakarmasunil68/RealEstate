package com.appentus.realestate.pojo;

import java.io.Serializable;

public class SizePOJO implements Serializable {
    String property_size_id;
    String size;
    String status;

    public String getProperty_size_id() {
        return property_size_id;
    }

    public void setProperty_size_id(String property_size_id) {
        this.property_size_id = property_size_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
