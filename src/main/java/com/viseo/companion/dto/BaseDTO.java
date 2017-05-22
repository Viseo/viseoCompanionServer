package com.viseo.companion.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {
    long id;
    long version;

    public BaseDTO(long id, long version) {
        this.id = id;
        this.version = version;
    }

    public BaseDTO() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
