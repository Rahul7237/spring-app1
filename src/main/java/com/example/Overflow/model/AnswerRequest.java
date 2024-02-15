package com.example.Overflow.model;

public class AnswerRequest {
    private int quesId;
    private String ansDesc;
    private String userId;
    private  String latitude;
    private String longitude;
    private String address;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public String getAnsDesc() {
        return ansDesc;
    }

    public void setAnsDesc(String ansDesc) {
        this.ansDesc = ansDesc;
    }
}
