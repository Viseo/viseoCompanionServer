package com.viseo.companion.ControlleurTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by HEL3666 on 25/04/2017.
 */
public class UzerControlleurTest {



    @Autowired
    private UzerService uzerService;

    @Test
    public void addUserTest() throws ClientProtocolException, IOException {
        final Uzer uzer = new Uzer();
        uzer.setEmail("iballlaz@gmail.com");
        uzer.setPassword("ibllaz");
        uzer.setFirstName("ibllaz");
        uzer.setLastName("bstllaz");
        // Création du client et éxécution d'une requete GET
        final org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        final HttpPost mockRequestPost = new HttpPost("http://localhost:8080/users");
        ObjectMapper mapper = new ObjectMapper();
        com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonInString = ow.writeValueAsString(uzer);
        // Ã©tablition de la requette (header+body)
        mockRequestPost.addHeader("content-type", "application/json");
        mockRequestPost.setEntity(new StringEntity(jsonInString));

        mapper.readValue(jsonInString, Uzer.class);


        HttpResponse mockResponse = client.execute(mockRequestPost);

        // Le code retour HTTP doit être un succès (200)
        Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

        final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
        final ObjectMapper mappe = new ObjectMapper();
        final Uzer us = mappe.readValue(rd, Uzer.class);
        uzerService.deletUzer(15l);




    }
}
