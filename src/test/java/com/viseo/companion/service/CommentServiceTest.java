package com.viseo.companion.service;

import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.Event;
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
public class CommentServiceTest {


    @Autowired
    UzerService uzerService;

    @Autowired
    EventService eventService;

    @Autowired
    CommentService commentService;

    @Test
    public void addCommentaireTest() {
        final Comment comment = new Comment();
        Calendar now = Calendar.getInstance();
        comment.setDatetime(now);
        comment.setCommentaire("cccccccccccccccccccccccccccccccccccccccccccccccccc");
        Uzer user = uzerService.getUser(1L);
        comment.setUzer(user);
        Event event = eventService.getEvent(2L);
        comment.setEvenement(event);
        //try {
           // Comment commentaire1 =  commentaireService.addComment(comment);
            //Assert.assertEquals(true, commentaire1);
            //Assert.assertNotNull(commentaire1.getId());
            //Assert.assertEquals(comment.getComment(), commentaire1.getComment());
           // comment = commentaire1;
        //} catch (CompanionException ex) {
            //throw new RuntimeException(ex);
        //}
         Boolean newComment = commentService.addComment(comment);
        Assert.assertEquals(true, newComment);

    }

    @Test
    public final void updateCommentaireTest() {
        final Long id = 3L;

        final Comment comment = commentService.getComment(id);
        comment.setCommentaire("haifa la meilleure");
        Calendar now = Calendar.getInstance();
        comment.setDatetime(now);
        Uzer user = uzerService.getUser(6L);
        comment.setUzer(user);
        Event event = eventService.getEvent(2L);
        comment.setEvenement(event);

        Comment newComment = commentService.updateComment(comment);
        Assert.assertNotNull(newComment);

    }
    @Test
    public final void deleteCommentaireTest() {
        final Long id = 1L;
        final Comment comment = commentService.getComment(id);
        try {
            commentService.deleteComment(id);
        } catch (final CompanionException ex) {
            Assert.assertEquals("This user cannot be deleted", ex.getMessage());


        }
    }

    @Test
    public void getCommentsByEvent() {
        Long eventId = 2L;
        List<Comment> comments = commentService.getCommentsByEvent(eventId);
        try {
            Assert.assertNotNull(comments);
            Assert.assertNotNull(comments.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }

    }
    @Test
    public final void getCommentairesTest() {
        final List<Comment> comments = commentService.getComents();
        try {
            Assert.assertEquals(16, comments.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }
    }
