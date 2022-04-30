package com.example.modelfashion.Model;

public class Notifi {
    private String IDNotifi;
    private String titleNotifi;
    private String contentNotifi;
    private String dateNotifi;

    public Notifi() {
    }

    public Notifi(String IDNotifi, String titleNotifi, String contentNotifi, String dateNotifi) {
        this.IDNotifi = IDNotifi;
        this.titleNotifi = titleNotifi;
        this.contentNotifi = contentNotifi;
        this.dateNotifi = dateNotifi;
    }

    public String getIDNotifi() {
        return IDNotifi;
    }

    public void setIDNotifi(String IDNotifi) {
        this.IDNotifi = IDNotifi;
    }

    public String getTitleNotifi() {
        return titleNotifi;
    }

    public void setTitleNotifi(String titleNotifi) {
        this.titleNotifi = titleNotifi;
    }

    public String getContentNotifi() {
        return contentNotifi;
    }

    public void setContentNotifi(String contentNotifi) {
        this.contentNotifi = contentNotifi;
    }

    public String getDateNotifi() {
        return dateNotifi;
    }

    public void setDateNotifi(String dateNotifi) {
        this.dateNotifi = dateNotifi;
    }
}
