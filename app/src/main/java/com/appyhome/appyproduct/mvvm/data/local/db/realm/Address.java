package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Address extends RealmObject {
    @PrimaryKey
    private String id;
    private String unitOrNumberHouse;
    private String areaLine1;
    private String areaLine2;
    private String streetName;
    private String city;
    private String country;
    private String postCode;
    private String phoneNumber;

    public String getUnitOrNumberHouse() {
        return unitOrNumberHouse;
    }

    public void setUnitOrNumberHouse(String unitOrNumberHouse) {
        this.unitOrNumberHouse = unitOrNumberHouse;
    }

    public String getAreaLine1() {
        return areaLine1;
    }

    public void setAreaLine1(String areaLine1) {
        this.areaLine1 = areaLine1;
    }

    public String getAreaLine2() {
        return areaLine2;
    }

    public void setAreaLine2(String areaLine2) {
        this.areaLine2 = areaLine2;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
