package com.viseo.companion.controller;

import com.viseo.companion.domain.Commentaire;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.CommentaireService;
import com.viseo.companion.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CommentaireController {

    @Autowired
    private EventService eventService;

    @Autowired
    CommentaireService commentaireService;

    @RequestMapping(value = "${endpoint.addComment}", method = POST)
    public boolean addCommentaire(@RequestBody Commentaire cm) {


        return commentaireService.addCommentaire(cm);
    }

    @RequestMapping(value = "${endpoint.deleteComment}", method = RequestMethod.DELETE)
    public Boolean removeComment(@PathVariable("commentId") long commentId) {
        return commentaireService.deleteCommentaire(commentId);
    }

    @RequestMapping(value = "${endpoint.updateComment}", method = RequestMethod.PUT)
    public final Commentaire updateCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire commentaire1 = null;
        try {
            commentaire1 = commentaireService.updateCommentaire(commentaire);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return commentaire;
    }

    @RequestMapping(value = "${endpoint.getCommentsByEvent}", method = RequestMethod.GET)
    public List<Commentaire> getCommentsByEvent(@PathVariable("eventId") long eventId) {
        return commentaireService.getCommentsByEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.getComments}", method = RequestMethod.GET)
    public List<Commentaire> getCommentaires() {
        return commentaireService.getComents();
    }

}
