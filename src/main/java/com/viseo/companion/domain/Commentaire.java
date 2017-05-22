package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Commentaire extends BaseEntity {

    private Calendar datetime;
    private String Commentaire;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Commentaire> commentaires;

    @JsonIgnore
    @ManyToOne()
    private Uzer uzer;


    @JsonIgnore
    @ManyToOne()
    private Event evenement;

    public Event getEvenement() {
        return evenement;
    }

    public Commentaire() {

        super();
    }

    public void setCommentaire(String commentaire) {
        Commentaire = commentaire;
    }

    public Commentaire(Calendar datetime, String commentaire, Set<com.viseo.companion.domain.Commentaire> commentaires, Uzer uzer, Event evenement) {
        this.datetime = datetime;
        Commentaire = commentaire;
        this.commentaires = commentaires;
        this.uzer = uzer;
        this.evenement = evenement;
    }



    /*public Commentaire(Calendar datetime, String commentaire, Uzer uzer, Event evenement ) {
        this.datetime = datetime;
        Commentaire = commentaire;
        this.commentaires = new HashSet<Commentaire>();
        this.uzer = uzer;
        this.evenement = evenement;


    }*/

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public Uzer getUzer() {
        return uzer;

    }

    public void setUzer(Uzer uzer) {
        this.uzer = uzer;
    }

    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }
}
