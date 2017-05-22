package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Comment extends BaseEntity {

    private Calendar datetime;
    private String content;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> subComments;

    @ManyToOne
    private Uzer uzer;

    @ManyToOne
    private Event event;

    public Event getEvent() {
        return event;
    }

    public Comment() {

        super();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(Calendar datetime, String content, Set<Comment> subComments, Uzer uzer, Event event) {
        this.datetime = datetime;
        this.content = content;
        this.subComments = subComments;
        this.uzer = uzer;
        this.event = event;
    }

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public Set<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(Set<Comment> subComments) {
        this.subComments = subComments;
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

    public void setEvent(Event event) {
        this.event = event;
    }
}
