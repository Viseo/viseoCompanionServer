package com.viseo.companion.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event extends BaseEntity {

    private long category;
    private String name;
    private Calendar datetime;
    private String description;
    private String keyWords = "";
    private String location;
    private String imageUrl = "";

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants = new HashSet<User>();

    @ManyToOne
    private User host;

    public Event() {
    }

    public Event(String name, Calendar date, String description, String keyWords, String location, String imageUrl) {
        this.name = name;
        this.datetime = date;
        this.description = description;
        this.keyWords = keyWords;
        this.location = location;
        this.category = 0;
        this.imageUrl = imageUrl;
    }

    public Event(Event newEvent) {
        this.name = newEvent.name;
        this.datetime = newEvent.datetime;
        this.description = newEvent.description;
        this.keyWords = newEvent.keyWords;
        this.location = newEvent.location;
        this.category = newEvent.category;
    }

    public void addParticipant(User participant) {
        participants.add(participant);
    }

    public void removeParticipant(User participant) {
        participants.remove(participant);
    }

    public long getCategory() {
        return category;
    }

    public String getName() {
        return this.name;
    }

    public Calendar getDatetime() {
        return this.datetime;
    }

    public String getDescription() {
        return description;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public String getLocation() {
        return this.location;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setName(String event) {
        this.name = event;
    }

    //TODO: Handle the 2h shift (with a localizer probably)
    public void setDatetime(Calendar date) {
        this.datetime = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyWords(String motclefs) {
        this.keyWords = motclefs;
    }

    public void setLocation(String lieu) {
        this.location = lieu;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public User getHost() {
        return host;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

	
	
	
	

