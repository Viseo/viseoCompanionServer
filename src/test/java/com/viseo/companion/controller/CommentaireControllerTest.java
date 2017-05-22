package com.viseo.companion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Commentaire;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.CommentaireService;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

/**
 * Created by HEL3666 on 15/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class CommentaireControllerTest {

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    UzerService uzerService;

    @Autowired
    EventService eventService;


    @Test
    public void addCommentaireTest() throws IOException {

        final Commentaire commentaire = new Commentaire();
        Calendar now = Calendar.getInstance();
        commentaire.setDatetime(now);
        commentaire.setCommentaire("haaaaaaaaaaas");
        Uzer user = uzerService.getUser(6L);
        commentaire.setUzer(user);
        Event event = eventService.getEvent(2L);
        commentaire.setEvenement(event);

        // Création du client et éxécution d'une requete POST
        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/comment");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(commentaire);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        final org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)z
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());



    }

    @Test
    public final void deleteCommentaireTest() throws ClientProtocolException, IOException {

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpDelete mockRequest = new HttpDelete("http://localhost:8080/comment/4");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
    }

    @Test
    public final void getCommentsByEventTest() throws IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/commentaire/events/2");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        org.junit.Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
        HttpEntity entity = mockResponse.getEntity();
        if (entity != null) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));

            try {
                final ObjectMapper map = new ObjectMapper();
                final Iterable<Commentaire> cm = map.readValue(rd, Iterable.class);
                Assert.assertNotNull(cm);
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public final void updateCommentaireTest() throws ClientProtocolException, IOException {
        Commentaire commentaire = commentaireService.getCommentaire(8);

        Calendar now = Calendar.getInstance();
        commentaire.setDatetime(now);
        commentaire.setCommentaire("meeeeeee");
        Uzer user = uzerService.getUser(1L);
        commentaire.setUzer(user);

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPut mockRequestPutt = new HttpPut("http://localhost:8080/comment/8");
        ObjectMapper mapper = new ObjectMapper();
        com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(commentaire);
        // Ã©tablition de la requette (header+body)
        mockRequestPutt.addHeader("content-type", "application/json");
        mockRequestPutt.setEntity(new StringEntity(jsonInString));
        HttpResponse mockResponse = client.execute(mockRequestPutt);
        final org.apache.http.HttpResponse mockResponses = client.execute(mockRequestPutt);
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
    }
    @Test
    public final void listCommentairesTest() throws ClientProtocolException, IOException {

        // Création du client et éxécution d'une requete GET
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet mockRequest = new HttpGet("http://localhost:8080/comments");
        final HttpResponse mockResponse = client.execute(mockRequest);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mapper = new ObjectMapper();
        List<Commentaire> commentaires = new ObjectMapper().readValue(rd, new TypeReference<List<Commentaire>>() {
        });
        Assert.assertNotNull(commentaires);
        System.out.println(commentaires.size());
    }

}
