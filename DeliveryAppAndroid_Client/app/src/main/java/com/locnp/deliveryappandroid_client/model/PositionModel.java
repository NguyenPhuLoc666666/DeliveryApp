package com.locnp.deliveryappandroid_client.model;

import androidx.annotation.NonNull;

public class PositionModel {
    int id;
    double latitude;
    double longitude;
    int priority;
    int testData;

    public int getTestData() {
        return testData;
    }

    public void setTestData(int testData) {
        this.testData = testData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NonNull
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", latitude='" + getLatitude() + '\'' +
                ", longitude='" + getLongitude() + '\'' +
                ", priority='" + getPriority() + '\'' +
                ", testData='" + getTestData() + '\'' +
                '}';
    }
}

