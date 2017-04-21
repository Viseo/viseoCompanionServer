
package com.viseo.companion.domain.apiextern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("custom_notification")
    @Expose
    private CustomNotification customNotification;

    public Data(CustomNotification customNotification) {
        this.customNotification = customNotification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomNotification getCustomNotification() {
        return customNotification;
    }

    public void setCustomNotification(CustomNotification customNotification) {
        this.customNotification = customNotification;
    }

}
