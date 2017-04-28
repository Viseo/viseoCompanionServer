package com.viseo.companion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        uzer.setEmail("haifaaa@gmail.com");
        uzer.setPassword("oui");
        uzer.setFirstName("oui");
        uzer.setLastName("oui");

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
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users/17");
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
    public final void getUsersTest() throws IOException {


        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mapper = new ObjectMapper();
        final Iterable<Uzer> uz = mapper.readValue(rd, Iterable.class);

        Assert.assertNotNull(uz);

    }
    @Test
    public final void getUsersByEmailTest() throws IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        String email="me@gmail.com";
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users/email");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        org.junit.Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

            try {
                final ObjectMapper map = new ObjectMapper();
                final Iterable<Uzer> uz = map.readValue(rd, Iterable.class);

                Assert.assertNotNull(uz);


            } catch (Exception ex) {
            }


        }

    }

    @Test
    public void AuthentificationTest() throws IOException {


        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/authenticate");
        final ObjectMapper mapper = new ObjectMapper();

        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("email", "haifaaa@gmail.com"));
        postParameters.add(new BasicNameValuePair("password", "oui"));

        mockRequestPost.setEntity(new UrlEncodedFormEntity(postParameters));

        mockRequestPost.addHeader("Content-type", "application/json");


        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final Uzer us = map.readValue(rd, Uzer.class);

    }

        @Test
        public void updateUserTest() throws ClientProtocolException, IOException{
            final Uzer uzer = uzerService.getUser(26L);
            uzer.setEmail("ihate@gmail.com");
            uzer.setPassword("bbbbbeeeeeeeeb");
            uzer.setLastName("haifa");


            final HttpClient client = HttpClientBuilder.create().build();
            final HttpPut mockPost = new HttpPut("http://localhost:8080/users/26");
            ObjectMapper mapper = new ObjectMapper();
            com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

            final String jsonInString = ow.writeValueAsString(uzer);
            // Ã©tablition de la requette (header+body)
            mockPost.addHeader("content-type", "application/json");
            mockPost.setEntity(new StringEntity(jsonInString));

            HttpResponse mockResponse = client.execute(mockPost);


            Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        /*    final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
            final Uzer uz = mapper.readValue(rd, Uzer.class);
*/

        }





        @SuppressWarnings("unchecked")
        @Test
        public final void deleteUzerTest() throws ClientProtocolException, IOException {

            final Uzer uzer = new Uzer();
            uzer.setEmail("HAIFAAAAA@gmail.com");
            uzer.setPassword("HAIFA");
            uzer.setFirstName("HAIFA");
            uzer.setLastName("haifa");


            //Uzer temp = uzerService.addUser(uzer);
            // Création du client et éxécution d'une requete GET
            final HttpClient client = HttpClientBuilder.create().build();
            final HttpDelete mockRequest = new HttpDelete("http://localhost:8080/users/20");
            final HttpResponse mockResponse = client.execute(mockRequest);

            // Le code retour HTTP doit être un succès (200)
            Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());


        }


    }
