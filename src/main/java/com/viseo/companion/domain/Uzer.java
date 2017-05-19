package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Uzer extends BaseEntity {

    String email;
    String firstName;
    String lastName;
    String password;

    @JsonIgnore
    @OneToMany(mappedBy = "host",fetch = FetchType.LAZY)
    private Set<Event> organisedEvents = new HashSet<>();



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "uzer", fetch = FetchType.LAZY)
    private Set<Commentaire> commentaires ;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<Event>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles ;




    public Uzer() {
    }

    public Uzer(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles=new HashSet<Role>();
        this.commentaires=new HashSet<Commentaire>();
        switch (this.password = password) {
        }
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setOrganisedEvents(Set<Event> organisedEvents) {
        this.organisedEvents = organisedEvents;
    }

    public Set<Event> getOrganisedEvents() {

        return organisedEvents;
    }

}
