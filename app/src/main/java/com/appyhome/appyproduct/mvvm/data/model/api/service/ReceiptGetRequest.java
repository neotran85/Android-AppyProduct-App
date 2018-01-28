package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReceiptGetRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    public ReceiptGetRequest(String id) {
        idNumber = id;
    }
    public ReceiptGetRequest() {

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
