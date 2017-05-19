package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Commentaire extends BaseEntity {

    private Calendar datetime;
    private String Commentaire;

    @JsonIgnore
    @OneToMany
    private Set<Commentaire> commentaires = new HashSet<Commentaire>();

    @ManyToOne
    private Uzer uzer;

    @ManyToOne
    private Event evenement;

    public Event getEvenement() {
        return evenement;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Uzer> users = new HashSet<Uzer>();

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String commentaire) {
        Commentaire = commentaire;
    }

    public Commentaire(){

    }

    public Commentaire(Calendar datetime, String commentaire, Set<com.viseo.companion.domain.Commentaire> commentaires, Uzer uzer, Event evenement, Set<Uzer> users) {
        this.datetime = datetime;
        Commentaire = commentaire;

        this.commentaires = commentaires;
        this.uzer = uzer;
        this.evenement = evenement;
        this.users = users;
    }

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

    public Uzer getUzer() {
        return uzer;
    }

    public void setUzer(Uzer uzer) {
        this.uzer = uzer;
    }
    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }
    public Set<Uzer> getUsers() {
        return users;
    }

    public void setUsers(Set<Uzer> users) {
        this.users = users;
    }
}
