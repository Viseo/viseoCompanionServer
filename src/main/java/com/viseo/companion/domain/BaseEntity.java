package com.viseo.companion.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@MappedSuperclass
@XmlRootElement
public class BaseEntity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;

    public BaseEntity() {
    }

    public long getVersion() {
        return version;
    }

    public long getId() {
        return id;
    }
}
