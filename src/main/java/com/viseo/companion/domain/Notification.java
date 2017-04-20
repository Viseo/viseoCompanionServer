package com.viseo.companion.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viseo.companion.domain.apiextern.CustomNotification;
import com.viseo.companion.domain.apiextern.Data;
import com.viseo.companion.domain.apiextern.NotificationSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Component
@PropertySource("classpath:keys.properties")
public class Notification {

    private static String fireBaseURL;
    private static String fireBaseServerKey;

    String title;
    String body;
    String sound = "default";
    String icon = "ic_notif";
    String color = "#498ff7";
    String data;

    public Notification() {
    }

    public Notification(String title, String body, String sound, String icon, String color, String data) {
        this.title = title;
        this.body = body;
        this.sound = sound;
        this.icon = icon;
        this.color = color;
        this.data = data;
    }

    public Notification(Event event) {
        this.title = event.getName();
        this.body = event.getDateTimeToString() + " - " + event.getPlace();
    }
    @Value( "${fireBase.URL}" )
    public void setFireBaseURL(String url){
        fireBaseURL = url;
    }

    @Value( "${fireBase.ServerKey}" )
    public  void setFireBaseServerKey(String key){
        fireBaseServerKey = key;
    }


    public boolean sendNotification() {
        CustomNotification customNotification = new CustomNotification();
        customNotification.setTitle(this.title);
        customNotification.setBody(this.body);
        customNotification.setColor(this.color);
        customNotification.setIcon(this.icon);
        customNotification.setPriority("high");
        customNotification.setShowInForeground("true");

        Data data = new Data();
        data.setCustomNotification(customNotification);

        NotificationSchema notificationSchema = new NotificationSchema();
        notificationSchema.setTo("/topics/newEvent");
        notificationSchema.setData(data);

        Gson gson = new GsonBuilder().create();
        String JSONrequest = gson.toJson(notificationSchema);

        byte[] utf8 = new byte[0];
        try {
            utf8 = JSONrequest.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + fireBaseServerKey);
        HttpEntity<String> entity = new HttpEntity<String>(new String(utf8), headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(fireBaseURL, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }
}

	
	
	
	
	

