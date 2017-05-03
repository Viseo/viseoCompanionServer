package com.viseo.companion.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LMA3606 on 22/03/2017.
 */

@Entity
public class Role extends BaseEntity {

    String name;
    String description;

    @ManyToMany(mappedBy = "roles")
    private Set<Uzer> users = new HashSet<Uzer>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role() {

    }

    public Role(String name, String description) {

        this.name = name;
        this.description = description;
    }
}
