package com.viseo.companion.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class PasswordResetToken extends BaseEntity{

    private static final int EXPIRATION = 60 * 24;

    private String token;

    @OneToOne(cascade= CascadeType.ALL)
    private Uzer uzer;

    private Date expiryDate;

    public Uzer getUzer() {
        return uzer;
    }

    public PasswordResetToken(String token, Uzer uzer) {
        this.token = token;
        this.uzer = uzer;
    }
}
