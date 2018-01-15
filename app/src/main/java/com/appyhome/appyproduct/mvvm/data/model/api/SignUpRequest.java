package com.appyhome.appyproduct.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignUpRequest {
        @Expose
        @SerializedName("first_name")
        private String firstName;

        @Expose
        @SerializedName("last_name")
        private String lastName;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("phone_number")
        private String phoneNumber;

        @Expose
        @SerializedName("password")
        private String password;

        public SignUpRequest(String firstName, String last_name,
                                  String email, String phoneNumber, String password) {
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.firstName = firstName;
            this.lastName = last_name;
            this.email = email;
        }
        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
}
