package com.app.deliverieslogs.models;

import java.io.Serializable;

/** Class is used to contains the location object
 *  @author mandroid_v2.0 */
public class Location implements Serializable {

    private double lat;
    private double lng;
    private String address;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
