package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Comment extends BaseEntity {

    private Calendar datetime;
    private String content;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @JsonIgnore
    @ManyToOne()
    private Uzer uzer;


    @JsonIgnore
    @ManyToOne()
    private Event evenement;

    public Event getEvenement() {
        return evenement;
    }

    public Comment() {

        super();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(Calendar datetime, String commentaire, Set<Comment> comments, Uzer uzer, Event evenement) {
        this.datetime = datetime;
        content = commentaire;
        this.comments = comments;
        this.uzer = uzer;
        this.evenement = evenement;
    }



    /*public Comment(Calendar datetime, String commentaire, Uzer uzer, Event evenement ) {
        this.datetime = datetime;
        Comment = commentaire;
        this.comments = new HashSet<Comment>();
        this.uzer = uzer;
        this.evenement = evenement;


    }*/

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
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
