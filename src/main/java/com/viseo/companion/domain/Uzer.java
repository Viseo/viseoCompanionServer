package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Uzer")
public class Uzer extends BaseEntity {

    String email;
    String firstName;
    String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @JsonIgnore
    @OneToMany(mappedBy = "host")
    private Set<Event> organisedEvents = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    private Set<Event> events = new HashSet<Event>();

    @ManyToMany
    private Set<Role> roles;

    public Uzer() {
    }

    public Uzer(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = new HashSet<Role>();
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
