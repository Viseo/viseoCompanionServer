package com.viseo.companion.domain;

import java.io.Serializable;

/**
 * Created by HEL3666 on 19/05/2017.
 */
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    long id;
    long version;

    public BaseDTO() {
        super();
    }

    public BaseDTO(long id, long version) {
        super();
        this.id = id;
        this.version = version;
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

    // Même chose ensuite que pour BaseEntity
}
