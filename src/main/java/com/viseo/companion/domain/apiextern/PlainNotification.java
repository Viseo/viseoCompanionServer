
package com.viseo.companion.domain.apiextern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlainNotification {

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("show_in_foreground")
    @Expose
    private String showInForeground;
    @SerializedName("click_action")
    @Expose
    private String click_action;
    @SerializedName("badge")
    @Expose
    private Integer badge;

    public PlainNotification(String body, String title, String color, String priority, String icon, long id, String showInForeground, String click_action, Integer badge) {
        this.body = body;
        this.title = title;
        this.color = color;
        this.priority = priority;
        this.icon = icon;
        this.id = id;
        this.showInForeground = showInForeground;
        this.click_action = click_action;
        this.badge = badge;
    }

//    public PlainNotification(String body, String title, String color, String priority, String icon, String showInForeground) {
//        this.body = body;
//        this.title = title;
//        this.color = color;
//        this.priority = priority;
//        this.icon = icon;
//        this.showInForeground = showInForeground;
//    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShowInForeground() {
        return showInForeground;
    }

    public void setShowInForeground(String showInForeground) {
        this.showInForeground = showInForeground;
    }

}
