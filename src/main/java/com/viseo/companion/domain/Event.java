package com.viseo.companion.domain;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;

@SuppressWarnings("serial")
@Entity
public class Event implements java.io.Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    private long category;
    private String name;
    private Calendar datetime;
    private String description;
    private String keyWords;
    private String place;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Uzer> participants = new HashSet<Uzer>();

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.event", cascade = CascadeType.ALL)
//	private Set<AccountEvent> accountEvents = new HashSet<AccountEvent>();

    public Event() {
    }

    public Event(String name, Calendar date, String description, String keyWords, String place) {
        this.name = name;
        this.datetime = date;
        this.description = description;
        this.keyWords = keyWords;
        this.place = place;
        this.category = 0;
    }

    public void addParticipant(Uzer participant) {
        participants.add(participant);
    }

    public void removeParticipant(Uzer participant) {
        participants.remove(participant);
    }

    public long getId() {
        return id;
    }

    public long getCategory() {
        return category;
    }

    public String getName() {
        return this.name;
    }

    public Date getDatetime() {
        return this.datetime.getTime();
    }

    public String getDateTimeToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM à HH:mm");
        return sdf.format(this.datetime.getTime());
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

}

	
	
	
	

