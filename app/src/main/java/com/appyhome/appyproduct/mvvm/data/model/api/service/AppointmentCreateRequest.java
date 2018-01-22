package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppointmentCreateRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("datetime")
    private String dateTime;

    @Expose
    @SerializedName("services")
    private String services;

    @Expose
    @SerializedName("additional")
    private String additional;

    public AppointmentCreateRequest() {

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        String result = "id_number: " + idNumber + " | ";
         result = result + address + " | ";
         result = result + services + " | ";
         result = result + dateTime + " | ";
         result = result + additional;
         return result;
    }
}
