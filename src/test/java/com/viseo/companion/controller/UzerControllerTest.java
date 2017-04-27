package com.viseo.companion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.apache.http.HttpEntity;
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

/**
 * Created by HEL3666 on 25/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class UzerControllerTest {


    @Autowired
    private UzerService uzerService;

    @Test
    public void addUserTest() throws IOException {
        final Uzer uzer = new Uzer();
        uzer.setEmail("HAIFAAAAA@gmail.com");
        uzer.setPassword("HAIFA");
        uzer.setFirstName("HAIFA");
        uzer.setLastName("haifa");

        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/adduser");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(uzer);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final Uzer us = map.readValue(rd, Uzer.class);

        // uzerService.deletUzer(us.getId());


    }

    @Test
    public final void getUserTest() throws IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users/3");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        org.junit.Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

            try {
                final ObjectMapper map = new ObjectMapper();
                final Uzer us = map.readValue(rd, Uzer.class);
                Assert.assertNotNull(us);



            } catch (Exception ex) {
            }


        }

    }

    @Test
    public void AuthentificationTest() throws IOException {
        final Uzer user = new Uzer();
        user.setEmail("haifa@gmail.com");
        user.setPassword("haifa");


        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/authenticate");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(user);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

            try {
                final ObjectMapper map = new ObjectMapper();
                final Uzer us = map.readValue(rd, Uzer.class);

            } catch (Exception ex) {
            }


        }
    }
}