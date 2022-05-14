package com.example.modelfashion.Model.response.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
        @SerializedName("message")
        private String message;
        @SerializedName("data")
        private String data;

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public String getData() {
                return data;
        }

        public void setData(String data) {
                this.data = data;
        }
}
