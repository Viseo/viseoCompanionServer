package com.viseo.companion.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Comment extends BaseEntity {

    private Calendar datetime;
    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne
    private Uzer uzer;

    @ManyToOne
    private Event event;

    @OneToMany
    List<Uzer> likers = new ArrayList<>();

    public Comment() {
        super();
    }

    public Comment(Calendar datetime, String content, List<Comment> children, Uzer uzer, Event event) {
        this.datetime = datetime;
        this.content = content;
        this.children = children;
        this.uzer = uzer;
        this.event = event;
    }

    public List<Uzer> getLikers() {
        return likers;
    }

    public void setLikers(List<Uzer> likers) {
        this.likers = likers;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event evenement) {
        this.event = evenement;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public void addChild(Comment child) {
        children.add(child);
    }

    public void removeChild(Comment comment) {
        children.remove(comment);
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

    public void addLiker(Uzer uzer) {
        if(uzer != null) {
            likers.add(uzer);
        }
    }

    public void removeliker(Uzer uzer) {
        likers.remove(uzer);
    }
}
