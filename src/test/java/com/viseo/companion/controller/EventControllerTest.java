package com.viseo.companion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.annotation.XmlElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IBO3693 on 27/04/2017.
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

        Calendar now = Calendar.getInstance();
        final Event event = new Event("ibtisAMOOOOO", now, "Hello World!", "HELLLLL", "HELLLLL");

        final Uzer uzer = uzerService.getUser(4L);
        event.addParticipant(uzer);

        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/addevent");
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(event);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)z
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
       /* final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final Event ev = map.readValue(rd, Event.class);*/

    }

    @Test
    public final void deleteEventTest() throws ClientProtocolException, IOException {

        //  Event eventDeleted=eventService.getEvent(14);+eventDeleted.getId()
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpDelete mockRequest = new HttpDelete("http://localhost:8080/events/40");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());


    }

    @Test
    public final void listEventsTest() throws ClientProtocolException, IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/events");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mapper = new ObjectMapper();
        // final Event ev = mapper.readValue(rd, Event.class);
        List<Event> Listevents = new ObjectMapper().readValue(rd, new TypeReference<List<Event>>() {
        });

        Assert.assertNotNull(Listevents);

    }

    @Test
    public final void getEventTest() throws ClientProtocolException, IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/events/15");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mapper = new ObjectMapper();
        final Event ev = mapper.readValue(rd, Event.class);

        Assert.assertNotNull(ev);


    }

    @Test
    public void updateEventTest() throws ClientProtocolException, IOException {
        Event event = new Event();
        try {
            Long id = 45L;

            if (eventService.getEvent(id) != null) {
                event = eventService.getEvent(id);
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        event.setDescription("HELLO SPEEDYYYY");

        Uzer user = uzerService.getUser(4L);

        event.getParticipants().remove(user);

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPut mockPost = new HttpPut("http://localhost:8080/events");
        ObjectMapper mapper = new ObjectMapper();
        com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(event);
        // Ã©tablition de la requette (header+body)
        mockPost.addHeader("content-type", "application/json");
        mockPost.setEntity(new StringEntity(jsonInString));
        HttpResponse mockResponse = client.execute(mockPost);

        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final Event event1 = mapper.readValue(rd, Event.class);


    }

    @Test
    public void getParticipants() throws IOException {
        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/participants/37");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mapper = new ObjectMapper();

        List<Uzer> ListParticipants = new ObjectMapper().readValue(rd, new TypeReference<List<Uzer>>() {
        });

        Assert.assertNotNull(ListParticipants);

    }
}
