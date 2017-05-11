package com.viseo.companion.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken extends BaseEntity{

    private static final int EXPIRATION = 60 * 24;

    private String guid;

    @OneToOne(cascade= CascadeType.ALL)
    private Uzer uzer;

    private Calendar expiryDate;

    public Uzer getUzer() {
        return uzer;
    }

    public PasswordResetToken(String guid, Uzer uzer) {
        this.guid = guid;
        this.uzer = uzer;
        setExpiryDate(2);
    }

    public PasswordResetToken() {
    }

    public String getGuid() {
        return guid;
    }

    public boolean isUnexpired(){
        return expiryDate.after(Calendar.getInstance());
    }

    private void setExpiryDate(int howManyHours) {
        expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.HOUR_OF_DAY, howManyHours);
    }
}
