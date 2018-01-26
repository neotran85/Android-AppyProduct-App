package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderCompletedRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    @Expose
    @SerializedName("rating")
    private String rating;

    @Expose
    @SerializedName("comments")
    private String comments;

    public OrderCompletedRequest(String idNumber, String comments, String rating) {
        this.idNumber = idNumber;
        this.comments = comments;
        this.rating = rating;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
