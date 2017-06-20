package com.viseo.companion.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Comment extends BaseEntity {

    private Calendar datetime;
    private String content;
    private boolean publish;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne
    private User user;

    @ManyToOne

    private Event event;

    @ManyToMany(fetch = FetchType.EAGER)
    List<User> likers = new ArrayList<>();

    public Comment() {
        super();
    }

    public Comment(Calendar datetime, String content, boolean publish, List<Comment> children, User user, Event event, List<User> likers) {
        this.datetime = datetime;
        this.content = content;
        this.publish = publish;
        this.children = children;
        this.user = user;
        this.event = event;
        this.likers = likers;
    }

    public boolean getPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addLiker(User user) {
        likers.add(user);
    }

    public void removeliker(User user) {
        likers.remove(user);
    }
}
