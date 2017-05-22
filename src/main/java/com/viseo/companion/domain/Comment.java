package com.viseo.companion.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Comment extends BaseEntity {

    private Calendar datetime;
    private String content;
    Comment parentComment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> children;

    @ManyToOne
    private Uzer uzer;

    @ManyToOne
    private Event event;

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
        if (children.indexOf(child) == -1) {
            if (child.parentComment != null) {
                child.parentComment.removeChild(child);
            }
            children.add(child);
            child.parentComment = this;
        }
    }

    public void removeChild(Comment comment) {
        if (children.indexOf(comment) != -1) {
            children.remove(comment);
            comment.parentComment = null;
        }
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
}
