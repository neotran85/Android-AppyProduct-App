package com.appyhome.appyproduct.mvvm.data.model.db;

public class ServiceAddress {
    public String number;
    public String street;
    public String area1;
    public String area2;
    public String city;
    public String code;

    public ServiceAddress() {
        number = "";
        street = "";
        area1 = "";
        area2 = "";
        city = "";
        code = "";
    }

    public ServiceAddress(String number, String street, String area1, String area2, String city, String code) {
        number = number;
        street = street;
        area1 = area1;
        area2 = area2;
        city = city;
        code = code;
    }
}
