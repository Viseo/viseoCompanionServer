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
    private String keyWords;
    private String place;
    private  String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Uzer> participants = new HashSet<Uzer>();

    @ManyToOne
    private Uzer host;

    public Event() {
    }

    public Event(String name, Calendar date, String description, String keyWords, String place, String imageUrl) {
        this.name = name;
        this.datetime = date;
        this.description = description;
        this.keyWords = keyWords;
        this.place = place;
        this.category = 0;
        this.imageUrl=imageUrl;
    }

    public Event(Event newEvent){
        this.name = newEvent.name;
        this.datetime = newEvent.datetime;
        this.description = newEvent.description;
        this.keyWords = newEvent.keyWords;
        this.place = newEvent.place;
        this.category = newEvent.category;
    }

    public void addParticipant(Uzer participant) {
        participants.add(participant);
    }

    public void removeParticipant(Uzer participant) {
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
        //.getTime();
    }

    public String getDescription() {
        return description;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public Set<Uzer> getParticipants() {
        return participants;
    }

    public String getPlace() {
        return this.place;
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

    public void setPlace(String lieu) {
        this.place = lieu;
    }

    public void setHost(Uzer host) {
        this.host = host;
    }

    public Uzer getHost() {
        return host;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

	
	
	
	

