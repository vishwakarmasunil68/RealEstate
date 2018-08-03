package com.appentus.realestate.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatMsgPOJO implements Serializable{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("fri_id")
    @Expose
    private String friId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_detail")
    @Expose
    private UserDetailPOJO userDetailPOJO;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFriId() {
        return friId;
    }

    public void setFriId(String friId) {
        this.friId = friId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDetailPOJO getUserDetailPOJO() {
        return userDetailPOJO;
    }

    public void setUserDetailPOJO(UserDetailPOJO userDetailPOJO) {
        this.userDetailPOJO = userDetailPOJO;
    }
}
