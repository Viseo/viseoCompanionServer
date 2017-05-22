package com.viseo.companion.service;

import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Commentaire;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

/**
 * Created by HEL3666 on 15/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class CommentaireServiceTest {


    @Autowired
    UzerService uzerService;

    @Autowired
    EventService eventService;

    @Autowired
    CommentaireService commentaireService;

    @Test
    public void addCommentaireTest() {
        final Commentaire commentaire = new Commentaire();
        Calendar now = Calendar.getInstance();
        commentaire.setDatetime(now);
        commentaire.setCommentaire("cccccccccccccccccccccccccccccccccccccccccccccccccc");
        Uzer user = uzerService.getUser(1L);
        commentaire.setUzer(user);
        Event event = eventService.getEvent(2L);
        commentaire.setEvenement(event);
        //try {
           // Commentaire commentaire1 =  commentaireService.addCommentaire(commentaire);
            //Assert.assertEquals(true, commentaire1);
            //Assert.assertNotNull(commentaire1.getId());
            //Assert.assertEquals(commentaire.getCommentaire(), commentaire1.getCommentaire());
           // commentaire = commentaire1;
        //} catch (CompanionException ex) {
            //throw new RuntimeException(ex);
        //}
         Boolean newComment = commentaireService.addCommentaire(commentaire);
        Assert.assertEquals(true, newComment);

    }

    @Test
    public final void updateCommentaireTest() {
        final Long id = 3L;

        final Commentaire commentaire = commentaireService.getCommentaire(id);
        commentaire.setCommentaire("haifa la meilleure");
        Calendar now = Calendar.getInstance();
        commentaire.setDatetime(now);
        Uzer user = uzerService.getUser(6L);
        commentaire.setUzer(user);
        Event event = eventService.getEvent(2L);
        commentaire.setEvenement(event);

        Commentaire newComment = commentaireService.updateCommentaire(commentaire);
        Assert.assertNotNull(newComment);

    }
    @Test
    public final void deleteCommentaireTest() {
        final Long id = 1L;
        final Commentaire commentaire = commentaireService.getCommentaire(id);
        try {
            commentaireService.deleteCommentaire(id);
        } catch (final CompanionException ex) {
            Assert.assertEquals("This user cannot be deleted", ex.getMessage());


        }
    }

    @Test
    public void getCommentsByEvent() {
        Long eventId = 2L;
        List<Commentaire> commentaires = commentaireService.getCommentsByEvent(eventId);
        try {
            Assert.assertNotNull(commentaires);
            Assert.assertNotNull(commentaires.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }

    }
    @Test
    public final void getCommentairesTest() {
        final List<Commentaire> commentaires = commentaireService.getComents();
        try {
            Assert.assertEquals(16, commentaires.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }
    }
