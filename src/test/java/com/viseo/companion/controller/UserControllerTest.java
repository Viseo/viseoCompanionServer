package com.viseo.companion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viseo.companion.domain.User;
import com.viseo.companion.service.UserService;
import org.apache.http.HttpEntity;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserService userService;
    /*
    @InjectMocks
    UzerController uzerController;

    @Mock
    UzerService uzerService;


    String url = "http://localhost:8080/users/adduser";

    @Test
    public void addUserTest() throws IOException {
        final User uzer = new User();
        uzer.setEmail("haifaaa@gmail.com");
        uzer.setPassword("nonnn");
        uzer.setFirstName("elhh");
        uzer.setLastName("haa");

        RestAssuredMockMvc.given().standaloneSetup(uzerController)
                .contentType("application/json").body(uzer).when()
                .post(url).then().statusCode(200);
    }
}*/

    @Test
    public void addUserTest() throws IOException {
        final User user = new User();
        user.setEmail("haifaa@gmail.com");
        user.setPassword("non");
        user.setFirstName("elh");
        user.setLastName("ha");

        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/users");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(user);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)z
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final User us = map.readValue(rd, User.class);
    }

    @Test
    public final void getUserTest() throws IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users/43");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        org.junit.Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
            try {
                final ObjectMapper map = new ObjectMapper();
                final User us = map.readValue(rd, User.class);
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
        final Iterable<User> uz = mapper.readValue(rd, Iterable.class);

        Assert.assertNotNull(uz);
    }

    @Test
    public final void getUsersByEmailTest() throws IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/users/emails/haifaa@gmail.com");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        org.junit.Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

            try {
                final ObjectMapper map = new ObjectMapper();
                final Iterable<User> uz = map.readValue(rd, Iterable.class);
                Assert.assertNotNull(uz);
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public void AuthentificationTest() throws IOException {
        final User user = new User();
        user.setEmail("haifaa@gmail.com");
        user.setPassword("non");

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
        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper map = new ObjectMapper();
        final User us = map.readValue(rd, User.class);
    }

    @Test
    public final void updateUserTest() throws ClientProtocolException, IOException {
        final User user = userService.getUser(23L);
        user.setEmail("ihate@gmail.com");
        user.setPassword("me");
        user.setLastName("haifa");

        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPut mockRequestPut = new HttpPut("http://localhost:8080/users/23");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(user);
        mockRequestPut.addHeader("Content-type", "application/json");
        mockRequestPut.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPut);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
    }

    @Test
    public final void deleteUzerTest() throws ClientProtocolException, IOException {
        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpDelete mockRequest = new HttpDelete("http://localhost:8080/users/19");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
    }
}
