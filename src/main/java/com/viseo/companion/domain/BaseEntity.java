package com.viseo.companion.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }
}
