package com.viseo.companion.domain.apiextern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("custom_notification")
    @Expose
    private PlainNotification customNotification;

    public Data(PlainNotification customNotification) {
        this.customNotification = customNotification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlainNotification getCustomNotification() {
        return customNotification;
    }

    public void setCustomNotification(PlainNotification customNotification) {
        this.customNotification = customNotification;
    }

}
