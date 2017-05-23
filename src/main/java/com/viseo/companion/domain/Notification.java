package com.viseo.companion.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viseo.companion.domain.apiextern.Data;
import com.viseo.companion.domain.apiextern.NotificationSchemaAndroid;
import com.viseo.companion.domain.apiextern.NotificationSchemaIOS;
import com.viseo.companion.domain.apiextern.PlainNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@PropertySource("classpath:keys.properties")
public class Notification {

    private static String fireBaseURL;
    private static String fireBaseServerKey;

    private String title;
    private String body;
    private String sound = "default";
    private String icon = "ic_notif";
    private String color = "#498ff7";
    private long id = 1;

    private String topic = "";

    @Value("${fireBase.URL}")
    public void setFireBaseURL(String url) {
        fireBaseURL = url;
    }

    @Value("${fireBase.ServerKey}")
    public void setFireBaseServerKey(String key) {
        fireBaseServerKey = key;
    }

    public Notification() {
    }

    public Notification(String title, String body, String sound, String icon, String color, String topic, long id) {
        this.title = title;
        this.body = body;
        this.sound = sound;
        this.icon = icon;
        this.color = color;
        this.topic = topic;
        this.id = id;
    }

    public Notification(Event event, String topic) {
        this.topic = topic;
        this.title = "Nouvel évènement : " + event.getName();
        this.body = getDateTimeToString(event.getDatetime().getTime()) + " - " + event.getPlace();
        this.id = event.getId();
    }

    public boolean sendNotification() {
        String androidNotification = buildAndroidNotification(this.topic + "Android");
        String iosNotification = buildIOSNotification(this.topic + "IOS");

        System.out.println("Android notification sent : " + pushNotification(androidNotification));
        System.out.println("IOS notification sent : " + pushNotification(iosNotification));

        return true;
    }

    private String buildAndroidNotification(String topic) {
        PlainNotification customNotification = new PlainNotification(
                this.body,
                this.title,
                this.color,
                "high",
                this.icon,
                this.id,
                "true",
                "DEFAULT_ACTION",
                1
        );

        Data data = new Data(customNotification);
        NotificationSchemaAndroid notificationSchemaAndroid = new NotificationSchemaAndroid(topic, data);

        return formatToJSON(notificationSchemaAndroid);
    }

    private String buildIOSNotification(String topic) {
        PlainNotification notification = new PlainNotification(
                this.body,
                this.title,
                this.color,
                "high",
                this.icon,
                this.id,
                "true",
                "DEFAULT_ACTION",
                1
        );

        NotificationSchemaIOS notificationSchemaIOS = new NotificationSchemaIOS(topic, notification);

        return formatToJSON(notificationSchemaIOS);
    }

    private boolean pushNotification(String JSONnotification) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + fireBaseServerKey);
        HttpEntity<String> entity = new HttpEntity<String>(new String(JSONnotification), headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(fireBaseURL, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }

    private String getDateTimeToString(Date datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM à HH:mm");
        return sdf.format(datetime.getTime());
    }

    private String formatToJSON(Object object) {
        Gson gson = new GsonBuilder().create();
        String JSONstring = gson.toJson(object);

        byte[] JSONrequestUTF8 = new byte[0];
        try {
            JSONrequestUTF8 = JSONstring.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(JSONrequestUTF8);
    }

}

	
	
	
	
	

