package com.viseo.companion.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Notation extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private Integer notation;

    private String avis;

    public Notation() {
        super();
    }

    public Notation(User user, Event event, Integer notation, String avis) {
        this.user = user;
        this.event = event;
        this.notation = notation;
        this.avis = avis;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public Integer getNotation() {
        return notation;
    }

    public String getAvis() {
        return avis;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
    }

    public void setAvis(String avis) {
        avis = avis;
    }


}

