package com.viseo.companion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.service.CommentService;
import com.viseo.companion.service.EventService;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommentControllerTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;


    @Test
    public void addCommentaireTest() throws IOException {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Cet évènènement était vraiment trop cool !");
        commentDTO.setDatetime(1492116035);
        commentDTO.setUserId(1);
        commentDTO.setEventId(2);

        // Création du client et éxécution d'une requete POST
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/comment");
        final ObjectMapper mapper = new ObjectMapper();
        final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(commentDTO);
        mockRequestPost.addHeader("Content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        org.apache.http.HttpResponse mockResponse = client.execute(mockRequestPost);

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
                final Iterable<Comment> cm = map.readValue(rd, Iterable.class);
                Assert.assertNotNull(cm);
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public final void updateCommentaireTest() throws ClientProtocolException, IOException {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(3);
        commentDTO.setVersion(0);
        commentDTO.setContent(" [edited] Cet évènènement était vraiment trop cool !");
        commentDTO.setDatetime(1492116035);

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPut mockRequestPutt = new HttpPut("http://localhost:8080/comment/8");
        ObjectMapper mapper = new ObjectMapper();
        com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final String jsonInString = ow.writeValueAsString(commentDTO);
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
        List<Comment> comments = new ObjectMapper().readValue(rd, new TypeReference<List<Comment>>() {
        });
        Assert.assertNotNull(comments);
        System.out.println(comments.size());
    }

}
