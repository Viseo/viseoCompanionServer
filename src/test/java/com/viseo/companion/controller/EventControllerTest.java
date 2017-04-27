package com.viseo.companion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by HEL3666 on 25/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class EventControllerTest {


    @Autowired
    private EventService eventService;
    @Autowired
    private UzerService uzerService;

    @Test
    public void addEventTest() throws IOException {
        final Event event = new Event();
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("NADAAAAA");
        event.setName("ibtissamEventttt");
        event.setKeyWords("HELLLLL");
        event.setPlace("HELLLLLL");
        final Uzer uzer = uzerService.getUser(1L);

        event.addParticipant(uzer);
        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/events");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(event);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final Event ev = map.readValue(rd, Event.class);

        eventService.deleteEvent(ev.getId());


    }


}