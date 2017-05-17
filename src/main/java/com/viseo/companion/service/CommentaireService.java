package com.viseo.companion.service;

import com.viseo.companion.dao.CommentaireRepository;
import com.viseo.companion.domain.Commentaire;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HEL3666 on 15/05/2017.
 */


@Service
public class CommentaireService {


    @Autowired
    private CommentaireRepository commentaireRepository;

    public boolean addCommentaire(Commentaire commentaire) {
        try {

            return commentaireRepository.addCommentaire(commentaire);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        /*Notification notif = new Notification(event, "/topics/newEvent/");
        notif.sendNotification();*/

    }


    public boolean deleteCommentaire(Long commentaireId) {
        if (commentaireRepository.getComment(commentaireId) != null) {
            commentaireRepository.deleteCommentaire(commentaireRepository.getComment(commentaireId));
            return true;
        }
        return false;
    }

    public Commentaire updateCommentaire(Commentaire commentaire) {
        return commentaireRepository.updateCommentaire(commentaire);
    }

    public List<Commentaire> getCommentsByEvent(long eventId) {
        return commentaireRepository.getCommentsByEvent(eventId);
    }



    public Commentaire getCommentaire(long id) {
       Commentaire commentaire = commentaireRepository.getComment(id);
        return commentaire;
    }

    public List<Commentaire> getComents() {
        List<Commentaire> commentaires = null;
        try {
            commentaires = commentaireRepository.getComments();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commentaires;
    }

}
