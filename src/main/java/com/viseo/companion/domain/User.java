package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Uzer")
public class User extends BaseEntity {
    String email;
    String firstName;
    String lastName;
    private String imageUrl = "";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToMany
    private Set<Role> roles;

    public User() {
    }

    public User(String email, String firstName, String lastName, String password, String imageUrl) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = new HashSet<Role>();
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
