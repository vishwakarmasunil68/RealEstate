package com.appentus.realestate.pojo.location;

import java.util.List;

/**
 * Created by sunil on 20-03-2018.
 */

public class LocationResponseListPOJO {

    boolean success;
    List<NewLocationPOJO> resultList;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<NewLocationPOJO> getResultList() {
        return resultList;
    }

    public void setResultList(List<NewLocationPOJO> resultList) {
        this.resultList = resultList;
    }
}
