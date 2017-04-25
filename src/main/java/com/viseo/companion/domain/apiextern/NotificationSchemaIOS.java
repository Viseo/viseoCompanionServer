
package com.viseo.companion.domain.apiextern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationSchemaIOS {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("notification")
    @Expose
    private PlainNotification notification;

    public NotificationSchemaIOS(String to, PlainNotification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public PlainNotification getNotification() {
        return notification;
    }

    public void setNotification(PlainNotification notification) {
        this.notification = notification;
    }

}
