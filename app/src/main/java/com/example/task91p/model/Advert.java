package com.example.task91p.model;

public class Advert {
    private String name, phoneNumber, description, date, latitude, longitude, type;
    private int advertID;

    public Advert() {
    }

    public Advert(int advertID, String name, String phoneNumber, String description, String date, String latitude, String longitude, String type) {
        this.advertID = advertID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.type = type;
        this.longitude = longitude;
    }

    public Advert(String name, String phoneNumber, String description, String date, String latitude, String longitude, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.type = type;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAdvertID() {
        return advertID;
    }

    public void setAdvertID(int advertID) {
        this.advertID = advertID;
    }
}
