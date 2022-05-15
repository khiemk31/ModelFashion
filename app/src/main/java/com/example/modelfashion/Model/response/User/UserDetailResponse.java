package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class UserDetailResponse {
    @SerializedName("data")
    private UserDetail data;

    public UserDetail getData() {
        return data;
    }

    public void setData(UserDetail data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }


    class UserDetail {
        @SerializedName("phone")
        private String phone;
        @SerializedName("user_name")
        private String userName;
        @SerializedName("date_of_birth")
        private String dateOfBirth;
        @SerializedName("address")
        private String address;
        @SerializedName("gender")
        private int gender;
        @SerializedName("avatar")
        private String avatar;

        public UserDetail(String phone, String userName, String dateOfBirth, String address, int gender, String avatar) {
            this.phone = phone;
            this.userName = userName;
            this.dateOfBirth = dateOfBirth;
            this.address = address;
            this.gender = gender;
            this.avatar = avatar;
        }


        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "{" +
                    "phone='" + phone + '\'' +
                    ", userName='" + userName + '\'' +
                    ", dateOfBirth='" + dateOfBirth + '\'' +
                    ", address='" + address + '\'' +
                    ", gender=" + gender +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
