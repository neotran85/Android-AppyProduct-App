package com.appyhome.appyproduct.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotificationResponse {

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<Notification> data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notification> getData() {
        return data;
    }

    public void setData(List<Notification> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationResponse)) return false;

        NotificationResponse that = (NotificationResponse) o;

        if (!statusCode.equals(that.statusCode)) return false;
        if (!message.equals(that.message)) return false;
        return data.equals(that.data);

    }

    @Override
    public int hashCode() {
        int result = statusCode.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    public static class Notification {

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("published_at")
        private String date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Notification dataItem = (Notification) o;
            if (!title.equals(dataItem.title)) return false;
            if (!description.equals(dataItem.description)) return false;
            return date.equals(dataItem.date);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + description.hashCode();
            result = 31 * result + date.hashCode();
            return result;
        }
        public Notification() {
            this.title = "Celebrate our Grand Opening";
            this.description = "Get your premiere concert ticket now worth RM80";
            this.date = "28 Feb 2018";
        }
    }
}