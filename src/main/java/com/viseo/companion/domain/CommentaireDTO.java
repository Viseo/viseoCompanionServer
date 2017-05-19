package com.viseo.companion.domain;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by HEL3666 on 19/05/2017.
 */
public class CommentaireDTO extends BaseDTO {
    private Calendar datetime;
    private String Commentaire;
    private Set<Commentaire> commentaires;
    private Uzer uzer;
    private Event evenement;

    public CommentaireDTO() {
        super();
    }

    public Calendar getDatetime(Calendar datetime) {
        return this.datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String commentaire) {
        Commentaire = commentaire;
    }

    public Set<com.viseo.companion.domain.Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<com.viseo.companion.domain.Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Uzer getUzer() {
        return uzer;
    }

    public void setUzer(Uzer uzer) {
        this.uzer = uzer;
    }

    public Event getEvenement(Event evenement) {
        return this.evenement;
    }

    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }
}
